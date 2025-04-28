package Controlador;

import Modelo.ModeloP;
import Vista.VistaP;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class ControladorP {
    private ModeloP modelo;
    private VistaP vista;

    public ControladorP(ModeloP modelo, VistaP vista) {
        this.modelo = modelo;
        this.vista = vista;

        // Listener de botones
        this.vista.mostrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarProductos();
            }
        });

        this.vista.guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarProducto();
            }
        });

        this.vista.editarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarProducto();
            }
        });

        this.vista.eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarProducto();
            }
        });

        this.vista.regresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                regresar();
            }
        });
    }

    private void mostrarProductos() {
        try {
            vista.modTabla.setRowCount(0); // Limpiar tabla
            for (String[] producto : modelo.obtenerProductos()) {
                vista.modTabla.addRow(producto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void guardarProducto() {
        try {
            modelo.guardarProducto(
                    vista.Idtext.getText(),
                    vista.nombretext.getText(),
                    vista.categoriatext.getText(),
                    vista.preciotext.getText(),
                    vista.cantidadtext.getText()
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
                    vista.Idtext.getText(),
                    vista.preciotext.getText(),
                    vista.cantidadtext.getText()
            );
            limpiarCampos();
            mostrarProductos();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void eliminarProducto() {
        try {
            modelo.eliminarProducto(vista.Idtext.getText());
            limpiarCampos();
            mostrarProductos();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void limpiarCampos() {
        vista.Idtext.setText("");
        vista.nombretext.setText("");
        vista.categoriatext.setText("");
        vista.preciotext.setText("");
        vista.cantidadtext.setText("");
    }

    private void regresar() {
        // Aquí deberías abrir la vista del administrador
        vista.dispose();
    }
}
