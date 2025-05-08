package Controlador;

import Modelo.ModeloCategoria;
import Modelo.ModeloP;
import Vista.AdministradorVista;
import Vista.VistaCategoria;
import Vista.VistaP;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ControladorP extends JFrame {
    private ModeloP modelo;
    private VistaP vistaP;
    private VistaCategoria vistaC;

    private AdministradorVista adminVista;
    private ModeloCategoria modeloCategoria;

    public ControladorP(ModeloP modelo, VistaP vistaP, AdministradorVista adminVista) {
        this.modelo = modelo;
        this.vistaP = vistaP;
        this.adminVista = adminVista;
        this.modeloCategoria = new ModeloCategoria(modelo.getConexion());

        // Listener de botones
        this.vistaP.getMostrarButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarProductos();
            }
        });

        this.vistaP.getGuardarButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarProducto();
            }
        });

        this.vistaP.getEditarButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarProducto();
            }
        });

        this.vistaP.getEliminarButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarProducto();
            }
        });

        this.vistaP.getRegresarButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                regresar();
            }
        });

        this.vistaP.newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VistaCategoria vistaCategoria = new VistaCategoria();
                ModeloCategoria modeloCategoria = new ModeloCategoria(modelo.getConexion());
                ControladorCategoria controlador = new ControladorCategoria(vistaCategoria, modeloCategoria);


                vistaCategoria.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent e) {
                        categoriasComboBox(); // recargar categorías
                    }
                });

                controlador.iniciarVista();
            }
        });

        this.vistaP.getDeleteCategory().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String nombreCategoria = (String) vistaP.getComboBoxCategoria().getSelectedItem();

                    if (nombreCategoria == null) {
                        JOptionPane.showMessageDialog(vistaP, "No hay categoría seleccionada.");
                        return;
                    }

                    int confirm = JOptionPane.showConfirmDialog(vistaP,
                            "¿Seguro que deseas eliminar la categoría \"" + nombreCategoria + "\"?",
                            "Confirmar eliminación",
                            JOptionPane.YES_NO_OPTION);

                    if (confirm == JOptionPane.YES_OPTION) {
                        boolean eliminada = modeloCategoria.eliminarCategoria(nombreCategoria);
                        if (eliminada) {
                            JOptionPane.showMessageDialog(vistaP, "Categoría eliminada.");
                            categoriasComboBox(); // actualizar combo
                        } else {
                            JOptionPane.showMessageDialog(vistaP,
                                    "No se puede eliminar La categoría está en uso por productos.",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(vistaP, "Error al eliminar la categoría.");
                    ex.printStackTrace();
                }
            }
        });




    }

    private void mostrarProductos() {
        try {
            vistaP.modTabla.setRowCount(0); // Limpiar tabla
            for (String[] producto : modelo.obtenerProductos()) {
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
                    vistaP.getIdtext().getText(),
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
            modelo.editarProducto(
                    vistaP.getIdtext().getText(),
                    vistaP.getPreciotext().getText()
            );
            limpiarCampos();
            mostrarProductos();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void eliminarProducto() {
        try {
            modelo.eliminarProducto(vistaP.getIdtext().getText());
            limpiarCampos();
            mostrarProductos();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void limpiarCampos() {
        vistaP.getIdtext().setText("");
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
            vistaP.getComboBoxCategoria().removeAllItems(); // limpiar
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

    public void iniciarVista() {
        categoriasComboBox();
        vistaP.mostrarVista();
    }

}

