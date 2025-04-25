package Controlador;

import Modelo.ModeloP;
import Vista.VistaP;

import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;

public class ControladorP {
    private VistaP vista;
    private ModeloP modelo;

    public ControladorP(VistaP vista, ModeloP modelo) {
        this.vista = vista;
        this.modelo = modelo;

        // Usando los getters
        vista.getMostrarButton().addActionListener(e -> {
            try {
                mostrarProductos();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        vista.getGuardarButton().addActionListener(e -> {
            try {
                guardarProducto();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        vista.getEditarButton().addActionListener(e -> {
            try {
                editarProducto();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        vista.getEliminarButton().addActionListener(e -> {
            try {
                eliminarProducto();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        vista.getRegresarButton().addActionListener(e -> vista.dispose());
    }

        private void mostrarProductos() throws SQLException {
            vista.actualizarTabla(modelo.mostrarProductos());
        }

    private void guardarProducto() throws SQLException {
        int id = Integer.parseInt(vista.getIdtext().getText());
        String nombre = vista.getNombretext().getText();
        String categoria = vista.getCategoriatext().getText();
        int precio = Integer.parseInt(vista.getPreciotext().getText());
        int cantidad = Integer.parseInt(vista.getCantidadtext().getText());

        if (modelo.guardarProducto(id, nombre, categoria, precio, cantidad)) {
            vista.limpiarCampos();
            mostrarProductos();
        }
    }

    private void editarProducto() throws SQLException {
        int id = Integer.parseInt(vista.getIdtext().getText());
        int precio = Integer.parseInt(vista.getPreciotext().getText());
        int cantidad = Integer.parseInt(vista.getCantidadtext().getText());

        if (modelo.editarProducto(id, precio, cantidad)) {
            vista.limpiarCampos();
            mostrarProductos();
        }
    }

    private void eliminarProducto() throws SQLException {
        int id = Integer.parseInt(vista.getIdtext().getText());

        if (modelo.eliminarProducto(id)) {
            vista.limpiarCampos();
            mostrarProductos();
        }
    }

    private Object[] getColumnNames(DefaultTableModel modelo) {
        int columnas = modelo.getColumnCount();
        Object[] nombres = new Object[columnas];
        for (int i = 0; i < columnas; i++) {
            nombres[i] = modelo.getColumnName(i);
        }
        return nombres;
    }
}
