package Controlador;

import Vista.VentaVista;
import Modelo.ModeloP;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;

public class VentaControlador {
    private VentaVista vista;
    private ModeloP modelo;
    private DefaultTableModel catalogoModel = new DefaultTableModel(new String[]{"ID", "Descripción", "Precio"}, 0);
    private DefaultTableModel pedidoModel = new DefaultTableModel(new String[]{"ID", "Cantidad", "Precio", "Subtotal"}, 0);

    public VentaControlador(VentaVista vista, ModeloP modelo) {
        this.vista = vista;
        this.modelo = modelo;

        vista.tableCatalogo.setModel(catalogoModel);
        vista.tablePedido.setModel(pedidoModel);

        vista.mostrarCatalogoButton.addActionListener(e -> mostrarCatalogo());
        vista.agregarProductoButton.addActionListener(e -> agregarProducto());
    }



    private void mostrarCatalogo() {
        try {
            catalogoModel.setRowCount(0);

            DefaultTableModel productos = modelo.mostrarProductos();

            if (productos.getRowCount() == 0) {
                JOptionPane.showMessageDialog(vista, "No hay productos disponibles en el catálogo.", "Información", JOptionPane.INFORMATION_MESSAGE);
            } else {
                for (int i = 0; i < productos.getRowCount(); i++) {
                    Object id = productos.getValueAt(i, 0);
                    Object descripcion = productos.getValueAt(i, 1);
                    Object precio = productos.getValueAt(i, 3);
                    catalogoModel.addRow(new Object[]{id, descripcion, precio});
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(vista, "Error de conexión con el servidor.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }



    private void agregarProducto() {
        try {
            int id = Integer.parseInt(vista.IdProducto.getText());
            int cantidad = Integer.parseInt(vista.CantidadP.getText());
            double precio = Double.parseDouble(vista.PrecioUnitario.getText());
            double subtotal = cantidad * precio;

            pedidoModel.addRow(new Object[]{id, cantidad, precio, subtotal});
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, "Datos inválidos. Verifica los campos de entrada.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
