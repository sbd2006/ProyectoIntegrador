package Controlador;

import Modelo.ModeloCategoria;
import Modelo.ModeloP;
import Vista.AdministradorVista;
import Vista.VistaCategoria;
import Vista.VistaP;
import javax.swing.*;
import java.sql.*;
import java.util.List;

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
            for (String[] producto : productos) {
                vistaP.modTabla.addRow(producto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void guardarProducto() {
        try {
            String nombreCategoria = (String) vistaP.getComboBoxCategoria().getSelectedItem();
            int idCategoria = modeloCategoria.obtenerOCrearCategoria(nombreCategoria);

            modelo.guardarProducto(
                    vistaP.getNombretext().getText(),
                    idCategoria,
                    vistaP.getPreciotext().getText()
            );

            limpiarCampos();
            mostrarProductos();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void editarProducto() {
        try {
            int fila = vistaP.getTabla().getSelectedRow();
            if (fila >= 0) {
                String nuevoPrecio = vistaP.getPreciotext().getText().trim();

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
