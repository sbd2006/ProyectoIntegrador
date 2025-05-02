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
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfPTable;
import java.io.File;
import java.io.FileOutputStream;
import java.awt.Desktop;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import Modelo.Venta;
import Modelo.DetalleVenta;



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

        vista.tableCatalogo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = vista.tableCatalogo.getSelectedRow();
                if (fila != -1) {
                    String id = vista.tableCatalogo.getValueAt(fila, 0).toString();
                    String nombre = vista.tableCatalogo.getValueAt(fila, 1).toString();
                    String precio = vista.tableCatalogo.getValueAt(fila, 3).toString();

                    vista.IdProducto.setText(id);
                    vista.PrecioUnitario.setText(precio);
                    vista.Total.setText("");  // reset total
                    vista.CantidadP.setText("");  // reset cantidad
                }
            }
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

            int idVenta = dao.insertarVenta(venta, listaDetalles);
            venta.setIdVenta(idVenta);
            for (DetalleVenta detalle : listaDetalles) {
                detalle.setIdVenta(idVenta);
            }

            dao.insertarDetalles(listaDetalles);
            generarFacturaPDF(venta, listaDetalles);

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

            // Deshabilitar si ya no hay productos
            if (listaDetalles.isEmpty()) {
                vista.finalizarVenta.setEnabled(false);
            }
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

    private void generarFacturaPDF(Venta venta, List<DetalleVenta> detalles) {
        Document document = new Document();
        try {
            String nombreArchivo = "Factura_PostresMariaJose_" + venta.getIdVenta() + ".pdf";
            PdfWriter.getInstance(document, new FileOutputStream(nombreArchivo));
            document.open();

            Font tituloFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Font textoFont = FontFactory.getFont(FontFactory.HELVETICA, 12);

            document.add(new Paragraph("POSTRES MARIA JOSE", tituloFont));
            document.add(new Paragraph("Factura N°: " + venta.getIdVenta(), textoFont));
            document.add(new Paragraph("Fecha: " + venta.getFecha(), textoFont));
            document.add(new Paragraph("\n"));

            document.add(new Paragraph("Detalles de la venta:", textoFont));
            document.add(new Paragraph("--------------------------------------", textoFont));

            for (DetalleVenta d : detalles) {
                document.add(new Paragraph("Producto: " + d.getDescripcion(), textoFont));
                document.add(new Paragraph("ID Producto: " + d.getIdProducto(), textoFont));
                document.add(new Paragraph("Cantidad: " + d.getCantidad(), textoFont));
                document.add(new Paragraph("Precio Unitario: $" + d.getPrecioUnitario(), textoFont));
                document.add(new Paragraph("Subtotal: $" + d.getTotalProducto(), textoFont));
                document.add(new Paragraph(" ", textoFont));
            }

            document.add(new Paragraph("--------------------------------------", textoFont));
            document.add(new Paragraph("Total a pagar: $" + venta.getTotal(), tituloFont));
            document.add(new Paragraph("\nGracias por su compra. ¡Vuelva pronto!", textoFont));

            document.close();
            JOptionPane.showMessageDialog(vista, "Factura generada correctamente.");
            Desktop.getDesktop().open(new File(nombreArchivo));
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(vista, "Error al generar factura: " + e.getMessage());
        }
    }




}
