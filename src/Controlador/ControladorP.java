package Controlador;

import Modelo.*;
import Vista.AdministradorVista;
import Vista.VistaCategoria;
import Vista.VistaP;
import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.*;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ControladorP extends JFrame {
    private ModeloP modelo;
    private VistaP vistaP;
    private AdministradorVista adminVista;
    private ModeloCategoria modeloCategoria;

    public ControladorP(ModeloP modelo, VistaP vistaP, AdministradorVista adminVista) {
        this.modelo = modelo;
        this.vistaP = vistaP;
        this.adminVista = adminVista;
        this.modeloCategoria = new ModeloCategoria(modelo.getConexion());

        vistaP.getMostrarButton().addActionListener(e -> mostrarProductos());
        vistaP.getGuardarButton().addActionListener(e -> guardarProducto());
        vistaP.getEditarButton().addActionListener(e -> editarProducto());
        vistaP.getEliminarButton().addActionListener(e -> eliminarProducto());
        vistaP.getRegresarButton().addActionListener(e -> regresar());

        vistaP.getPreciotext().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                JTextField campo = vistaP.getPreciotext();
                int pos = campo.getCaretPosition();
                String texto = campo.getText().replace(".", "");

                if (!texto.isEmpty() && texto.matches("\\d+")) {
                    try {
                        long valor = Long.parseLong(texto);
                        String formateado = String.format("%,d", valor).replace(",", ".");
                        campo.setText(formateado);
                        if (pos > formateado.length()) pos = formateado.length();
                        campo.setCaretPosition(pos);
                    } catch (NumberFormatException ignored) {}
                }
            }
        });



        vistaP.newButton.addActionListener(e -> {
            VistaCategoria vistaCategoria = new VistaCategoria();
            ModeloCategoria modeloCategoria = new ModeloCategoria(modelo.getConexion());
            ControladorCategoria controlador = new ControladorCategoria(vistaCategoria, modeloCategoria);


            vistaCategoria.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    categoriasComboBox();
                }
            });

            controlador.iniciarVista();
        });

        vistaP.getDeleteCategory().addActionListener(e -> eliminarCategoria());
    }

    private void mostrarProductos() {
        try {
            vistaP.modTabla.setRowCount(0);
            List<String[]> productos = modelo.obtenerProductos();
            NumberFormat format = NumberFormat.getNumberInstance(Locale.GERMANY);

            for (String[] producto : productos) {
                producto[3] = format.format(Double.parseDouble(producto[3]));
                vistaP.modTabla.addRow(producto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void guardarProducto() {
        String nombre = vistaP.getNombretext().getText().trim();
        String precio = vistaP.getPreciotext().getText().trim();
        String nombreCategoria = (String) vistaP.getComboBoxCategoria().getSelectedItem();

        if (nombre.isEmpty() || precio.isEmpty() || nombreCategoria == null || nombreCategoria.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor rellene todos los campos.", "Campos incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int idCategoria = modeloCategoria.obtenerOCrearCategoria(nombreCategoria);

            modelo.guardarProducto(
                    nombre,
                    idCategoria,
                    precio.replace(".", "")
            );

            JOptionPane.showMessageDialog(null, "Producto guardado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

            limpiarCampos();
            mostrarProductos();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al guardar el producto: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editarProducto() {
        try {
            int fila = vistaP.getTabla().getSelectedRow();
            if (fila >= 0) {
                String nuevoPrecio = vistaP.getPreciotext().getText().trim().replace(".", "");

                if (nuevoPrecio.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor ingrese un precio.");
                    return;
                }

                String idProducto = vistaP.getTabla().getValueAt(fila, 0).toString();
                modelo.editarProducto(idProducto, nuevoPrecio);

                limpiarCampos();
                mostrarProductos();
            } else {
                JOptionPane.showMessageDialog(null, "Selecciona un producto para editar.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void eliminarProducto() {
        try {
            int fila = vistaP.getTabla().getSelectedRow();
            if (fila >= 0) {
                String idProducto = vistaP.getTabla().getValueAt(fila, 0).toString();

                modelo.eliminarProducto(idProducto);
                limpiarCampos();
                mostrarProductos();
            } else {
                JOptionPane.showMessageDialog(null, "Selecciona un producto para eliminar.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void limpiarCampos() {
        vistaP.getNombretext().setText("");
        vistaP.getComboBoxCategoria().setSelectedIndex(-1);
        vistaP.getPreciotext().setText("");
    }

    private void regresar() {
        vistaP.dispose();
        adminVista.setVisible(true);
    }

    public void categoriasComboBox() {
        try {
            vistaP.getComboBoxCategoria().removeAllItems();
            String sql = "SELECT Nombre FROM Categoria";
            PreparedStatement ps = modelo.getConexion().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                vistaP.getComboBoxCategoria().addItem(rs.getString("Nombre"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void eliminarCategoria() {
        try {
            String nombreCategoria = (String) vistaP.getComboBoxCategoria().getSelectedItem();
            if (nombreCategoria == null) {
                JOptionPane.showMessageDialog(vistaP, "No hay categoría seleccionada.");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(vistaP,
                    "¿Seguro que deseas eliminar la categoría \"" + nombreCategoria + "\"?",
                    "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                boolean eliminada = modeloCategoria.eliminarCategoria(nombreCategoria);
                if (eliminada) {
                    JOptionPane.showMessageDialog(vistaP, "Categoría eliminada.");
                    categoriasComboBox();
                } else {
                    JOptionPane.showMessageDialog(vistaP,
                            "No se puede eliminar. La categoría está en uso por productos.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(vistaP, "Error al eliminar la categoría.");
            ex.printStackTrace();
        }
    }

    public void iniciarVista() {
        categoriasComboBox();
        vistaP.mostrarVista();
    }

}
