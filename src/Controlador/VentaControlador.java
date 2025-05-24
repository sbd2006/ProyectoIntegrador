package Controlador;

import Modelo.*;
import Vista.EmpleadoVista;
import Vista.VentaVista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import java.io.File;
import java.io.FileOutputStream;
import java.awt.Desktop;
import java.text.DecimalFormat;

public class VentaControlador {
    private final VentaVista vista;
    private final EmpleadoVista emVista;
    private final VentaDAO dao;
    private final List<DetalleVenta> listaDetalles = new ArrayList<>();
    private String clienteNombreActual = "";
    private final int idEmpleadoActual;
    private String nombreEmpleadoActual = "";
    private final MovimientoDAO movimientoDAO = new MovimientoDAO();
    private final DecimalFormat formatoPunto = new DecimalFormat("#,###");

    public VentaControlador(VentaVista vista, VentaDAO dao, EmpleadoVista emVista, int idEmpleadoActual) {
        this.vista = vista;
        this.dao = dao;
        this.emVista = emVista;
        this.idEmpleadoActual = idEmpleadoActual;

        vista.FechaVenta.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        vista.getCambio().setEditable(false);
        vista.finalizarVenta.setEnabled(false);

        vista.agregarProductoButton.addActionListener(e -> agregarProducto());
        vista.finalizarVenta.addActionListener(e -> confirmarVenta());
        vista.regresarButton.addActionListener(e -> regresar());
        vista.mostrarButton.addActionListener(e -> mostrarCatalogo());
        vista.eliminarButton.addActionListener(e -> eliminarProducto());

        vista.PrecioUnitario.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                calcularTotalProducto();
            }
        });
        vista.CantidadP.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                calcularTotalProducto();
            }
        });

        vista.getDineroRecibido().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                actualizarCambio();
            }
        });

        vista.tableCatalogo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = vista.tableCatalogo.getSelectedRow();
                if (fila != -1) {
                    String id = vista.tableCatalogo.getValueAt(fila, 0).toString();
                    String precio = vista.tableCatalogo.getValueAt(fila, 3).toString();
                    vista.IdProducto.setText(id);
                    vista.PrecioUnitario.setText(precio);
                    vista.Total.setText("");
                    vista.CantidadP.setText("");
                }

            }
        });
    }

    private void actualizarCambio() {
        try {
            String dineroTexto = vista.getDineroRecibido().getText();
            double total = listaDetalles.stream().mapToDouble(DetalleVenta::getTotalProducto).sum();
            vista.Total.setText(formatoPunto.format(total));

            if (!dineroTexto.isBlank()) {
                double recibido = Double.parseDouble(dineroTexto.replace(".", ""));
                double cambio = recibido - total;
                vista.getCambio().setText(formatoPunto.format(cambio));
                if (recibido >= total) {
                    vista.getDineroRecibido().setBackground(Color.WHITE);
                    vista.finalizarVenta.setEnabled(true);
                } else {
                    vista.getDineroRecibido().setBackground(new Color(255, 128, 128));
                    vista.finalizarVenta.setEnabled(false);
                }
            } else {
                vista.getCambio().setText("0");
                vista.getDineroRecibido().setBackground(Color.WHITE);
                vista.finalizarVenta.setEnabled(false);
            }
        } catch (NumberFormatException e) {
            vista.getCambio().setText("0");
            vista.getDineroRecibido().setBackground(new Color(255, 128, 128));
            vista.finalizarVenta.setEnabled(false);
        }
    }


    private void agregarProducto() {
        try {
            if (vista.IdProducto.getText().isBlank() || vista.CantidadP.getText().isBlank()
                    || vista.PrecioUnitario.getText().isBlank() || vista.Total.getText().isBlank()) {
                JOptionPane.showMessageDialog(vista, "Todos los campos deben estar completos");
                return;
            }

            int cantidad = Integer.parseInt(vista.CantidadP.getText());
            double precio = Double.parseDouble(vista.PrecioUnitario.getText());
            double totalProducto = Double.parseDouble(vista.Total.getText().replace(".", "").replace(",", "."));
            String idProducto = vista.IdProducto.getText();

            if (cantidad <= 0 || precio <= 0 || totalProducto <= 0) {
                JOptionPane.showMessageDialog(vista, "Los valores deben ser mayores a cero.");
                return;
            }

            int stockActual = dao.obtenerStockActual(idProducto);
            if (cantidad > stockActual) {
                JOptionPane.showMessageDialog(vista, "Stock insuficiente. Disponible: " + stockActual);
                return;
            }

            String nombreProducto = dao.obtenerNombreProducto(idProducto);

            DetalleVenta detalle = new DetalleVenta(0, idProducto, cantidad, nombreProducto, precio, totalProducto);
            listaDetalles.add(detalle);

            vista.modTablaVenta.addRow(new Object[]{
                    idProducto, nombreProducto, cantidad, formatoPunto.format(precio), formatoPunto.format(totalProducto)
            });

            actualizarCambio();

        } catch (NumberFormatException | SQLException ex) {
            JOptionPane.showMessageDialog(vista, "Error al agregar producto: " + ex.getMessage());
        }
    }

    private void confirmarVenta() {
        try {
            String nombre = vista.getNombreCliente().getText().trim();
            String telefono = vista.getTelefono().getText().trim();
            String direccion = vista.getDireccion().getText().trim();

            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Debe ingresar el nombre del cliente.");
                return;
            }

            this.clienteNombreActual = nombre;
            ClienteDAO clienteDAO = new ClienteDAO();
            int clienteId = clienteDAO.obtenerIdOCrear(nombre, telefono, direccion);

            double total = listaDetalles.stream().mapToDouble(DetalleVenta::getTotalProducto).sum();
            vista.Total.setText(formatoPunto.format(total));

            double dineroRecibido = Double.parseDouble(vista.getDineroRecibido().getText().replace(".", ""));
            double cambio = dineroRecibido - total;
            vista.getCambio().setText(formatoPunto.format(cambio));

            Object[] options = {"Sí", "No"};
            int confirmacion = JOptionPane.showOptionDialog(vista, "¿Desea imprimir el recibo de venta?", "Confirmación",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

            Venta venta = new Venta(0, vista.FechaVenta.getText(), total, idEmpleadoActual, clienteId);

            venta.setMetodoPago(vista.getComboMetodoPago().getSelectedItem().toString());



            EmpleadoDAO empleadoDAO = new EmpleadoDAO();
            this.nombreEmpleadoActual = empleadoDAO.obtenerNombreEmpleado(idEmpleadoActual);
            boolean exitoVenta = dao.registrarVentaCompleta(venta, listaDetalles);

            vista.modTablaVenta.setRowCount(0);
            String fecha = vista.FechaVenta.getText();
            String tipoMovimiento = "Ajuste negativo inventario";
            String nroDocumento = "VENTA-" + venta.getIdVenta();

            for (DetalleVenta d : listaDetalles) {
                ModeloMovimiento movimiento = new ModeloMovimiento();
                movimiento.setCantidad(d.getCantidad());
                movimiento.setFechaMovimiento(fecha);
                movimiento.setObservacion("Venta");
                movimiento.setProductoId(Integer.parseInt(d.getIdProducto()));
                movimiento.setTipoMovimiento(tipoMovimiento);
                movimientoDAO.registrarMovimiento(movimiento, nroDocumento);
            }

            if (exitoVenta) {
                generarFacturaPDF(venta, listaDetalles, telefono, direccion, dineroRecibido, cambio);
                if (confirmacion == JOptionPane.YES_OPTION) {
                    Desktop.getDesktop().open(new File("facturas/Factura_PostresMariaJose_" + venta.getIdVenta() + ".pdf"));
                }
                JOptionPane.showMessageDialog(vista, "Venta registrada con éxito");
                vista.Total.setText("");
                vista.CantidadP.setText("");
                vista.getDineroRecibido().setText("");
                vista.getCambio().setText("");
                vista.getNombreCliente().setText("");
                vista.getTelefono().setText("");
                vista.getDireccion().setText("");
                vista.finalizarVenta.setEnabled(false);
                listaDetalles.clear();
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al finalizar venta: " + ex.getMessage());
        }
    }


    private void generarFacturaPDF(Venta venta, List<DetalleVenta> detalles, String telefono, String direccion, double dineroRecibido, double cambio) {
        Document document = new Document();
        try {
            File folder = new File("facturas");
            if (!folder.exists()) folder.mkdirs();

            String nombreArchivo = "facturas/Factura_PostresMariaJose_" + venta.getIdVenta() + ".pdf";
            PdfWriter.getInstance(document, new FileOutputStream(nombreArchivo));
            document.open();

            Font tituloFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Font textoFont = FontFactory.getFont(FontFactory.HELVETICA, 12);

            document.add(new Paragraph("POSTRES MARIA JOSE", tituloFont));
            document.add(new Paragraph("Factura N°: " + venta.getIdVenta(), textoFont));
            document.add(new Paragraph("Fecha: " + venta.getFecha(), textoFont));
            document.add(new Paragraph("Cliente: " + clienteNombreActual, textoFont));
            if (!telefono.isBlank()) document.add(new Paragraph("Teléfono: " + telefono, textoFont));
            if (!direccion.isBlank()) document.add(new Paragraph("Dirección: " + direccion, textoFont));
            document.add(new Paragraph("Empleado: " + nombreEmpleadoActual, textoFont));
            document.add(new Paragraph("\nDetalles de la venta:", textoFont));
            document.add(new Paragraph("--------------------------------------", textoFont));

            for (DetalleVenta d : detalles) {
                document.add(new Paragraph("Producto: " + d.getDescripcion(), textoFont));
                document.add(new Paragraph("Cantidad: " + d.getCantidad(), textoFont));
                document.add(new Paragraph("Precio Unitario: $" + formatoPunto.format(d.getPrecioUnitario()), textoFont));
                document.add(new Paragraph("Total: $" + formatoPunto.format(d.getTotalProducto()), textoFont));
                document.add(new Paragraph(" ", textoFont));
            }

            document.add(new Paragraph("--------------------------------------", textoFont));
            document.add(new Paragraph("Total: $" + formatoPunto.format(venta.getTotal()), tituloFont));
            document.add(new Paragraph("Dinero recibido: $" + formatoPunto.format(dineroRecibido), textoFont));
            document.add(new Paragraph("Cambio entregado: $" + formatoPunto.format(cambio), textoFont));
            document.add(new Paragraph("\nGracias por su compra.", textoFont));
            document.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error al generar PDF: " + e.getMessage());
        }
    }

    private void mostrarCatalogo() {
        try {
            vista.modTablaCatalogo.setRowCount(0);
            for (String[] prod : dao.obtenerProductos()) {
                vista.modTablaCatalogo.addRow(prod);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(vista, "Error al cargar catálogo: " + e.getMessage());
        }
    }

    private void eliminarProducto() {
        int fila = vista.tableVenta.getSelectedRow();
        if (fila != -1) {
            vista.modTablaVenta.removeRow(fila);
            listaDetalles.remove(fila);
            actualizarCambio();
        } else {
            JOptionPane.showMessageDialog(vista, "Seleccione una fila");
        }
    }

    private void calcularTotalProducto() {
        try {
            int cantidad = Integer.parseInt(vista.CantidadP.getText());
            double precio = Double.parseDouble(vista.PrecioUnitario.getText());
            vista.Total.setText(formatoPunto.format(cantidad * precio));
        } catch (NumberFormatException ignored) {}
    }

    private void regresar() {
        vista.dispose();
        emVista.setVisible(true);
    }
}
