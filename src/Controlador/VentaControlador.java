package Controlador;

import Modelo.*;
import Vista.VentaVista;

import javax.swing.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;

public class VentaControlador {
    private final VentaVista vista;
    private final VentaDAO dao;
    private final List<DetalleVenta> listaDetalles = new ArrayList<>();

    public VentaControlador(VentaVista vista, VentaDAO dao) {
        this.vista = vista;
        this.dao = dao;

        vista.FechaVenta.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        vista.agregarProductoButton.addActionListener(e -> agregarProducto());
        vista.finalizarVenta.addActionListener(e -> confirmarVenta());
        vista.regresarButton.addActionListener(e -> vista.dispose());
        vista.mostrarButton.addActionListener(e -> mostrarCatalogo());
        vista.eliminarButton.addActionListener(e -> eliminarProducto());

        vista.PrecioUnitario.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) { calcularTotalProducto(); }
        });
        vista.CantidadP.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) { calcularTotalProducto(); }
        });
    }

    private void agregarProducto() {
        try {
            if (vista.IdProducto.getText().isBlank() || vista.CantidadP.getText().isBlank()
                    || vista.PrecioUnitario.getText().isBlank()
                    || vista.Total.getText().isBlank()) {
                JOptionPane.showMessageDialog(vista, "Todos los campos deben estar completos", "Campos incompletos", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int cantidad = Integer.parseInt(vista.CantidadP.getText());
            double precio = Double.parseDouble(vista.PrecioUnitario.getText());
            double totalProducto = Double.parseDouble(vista.Total.getText().replace(",", "."));
            String idProducto = vista.IdProducto.getText();

            if (cantidad <= 0 || precio <= 0 || totalProducto <= 0) {
                JOptionPane.showMessageDialog(vista, "Los valores numéricos deben ser mayores a cero.", "Valor inválido", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Obtener nombre del producto desde la base de datos
            String nombreProducto = dao.obtenerNombreProducto(idProducto);

            DetalleVenta detalle = new DetalleVenta(
                    0,
                    idProducto,
                    cantidad,
                    nombreProducto,
                    precio,
                    totalProducto
            );
            listaDetalles.add(detalle);

            // Agregar fila con el nombre del producto incluido
            vista.modTablaVenta.addRow(new Object[]{
                    idProducto,
                    nombreProducto,
                    cantidad,
                    String.format("%.2f", precio),
                    String.format("%.2f", totalProducto)
            });

            JOptionPane.showMessageDialog(vista, "Producto agregado");

            if (!listaDetalles.isEmpty()) {
                vista.finalizarVenta.setEnabled(true);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, "Error en el formato de número: " + ex.getMessage(), "Formato inválido", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(vista, "Error al obtener el nombre del producto: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al agregar producto: " + ex.getMessage());
        }
    }


    private void confirmarVenta() {
        try {
            double total = listaDetalles.stream().mapToDouble(DetalleVenta::getTotalProducto).sum();
            Venta venta = new Venta(0, vista.FechaVenta.getText(), total);

            int idVenta = dao.insertarVenta(venta); // SE GENERA ID
            for (DetalleVenta detalle : listaDetalles) {
                detalle.setIdVenta(idVenta);
            }

            dao.insertarDetalles(listaDetalles); // Ahora los detalles tienen un ID_VENTA válido
            JOptionPane.showMessageDialog(vista, "Venta registrada con éxito");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error al registrar venta: " + e.getMessage());
        }
    }

    private void mostrarCatalogo() {
        try {
            vista.modTablaCatalogo.setRowCount(0);
            for (String[] producto : dao.obtenerProductos()) {
                vista.modTablaCatalogo.addRow(producto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(vista, "Error al mostrar productos: " + e.getMessage());
        }
    }

    private void eliminarProducto() {
        int fila = vista.tableVenta.getSelectedRow();
        if (fila != -1) {
            vista.modTablaVenta.removeRow(fila);
            listaDetalles.remove(fila);
            JOptionPane.showMessageDialog(vista, "Producto eliminado");
        } else {
            JOptionPane.showMessageDialog(vista, "Seleccione una fila para eliminar");
        }
    }

    private void calcularTotalProducto() {
        try {
            int cantidad = Integer.parseInt(vista.CantidadP.getText());
            double precio = Double.parseDouble(vista.PrecioUnitario.getText());
            vista.Total.setText(String.format("%.2f", cantidad * precio));
        } catch (NumberFormatException ignored) {
        }
    }
}
