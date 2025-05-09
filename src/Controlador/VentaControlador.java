package Controlador;

import Modelo.*;
import Vista.EmpleadoVista;
import Vista.VentaVista;

import javax.swing.*;
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

public class VentaControlador {
    private final VentaVista vista;
    private EmpleadoVista emVista;
    private final VentaDAO dao;
    private final List<DetalleVenta> listaDetalles = new ArrayList<>();

    private String clienteNombreActual = "";
    private final int idEmpleadoActual;
    private String nombreEmpleadoActual = "";

    private final MovimientoDAO movimientoDAO = new MovimientoDAO();


    public VentaControlador(VentaVista vista, VentaDAO dao, EmpleadoVista emVista, int idEmpleadoActual) {
        this.vista = vista;
        this.dao = dao;
        this.emVista = emVista;
        this.idEmpleadoActual = idEmpleadoActual;

        vista.FechaVenta.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

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

    private void agregarProducto() {
        try {
            if (vista.IdProducto.getText().isBlank() || vista.CantidadP.getText().isBlank()
                    || vista.PrecioUnitario.getText().isBlank() || vista.Total.getText().isBlank()) {
                JOptionPane.showMessageDialog(vista, "Todos los campos deben estar completos", "Campos incompletos", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int cantidad = Integer.parseInt(vista.CantidadP.getText());
            double precio = Double.parseDouble(vista.PrecioUnitario.getText());
            double totalProducto = Double.parseDouble(vista.Total.getText().replace(",", "."));
            String idProducto = vista.IdProducto.getText();

            if (cantidad <= 0 || precio <= 0 || totalProducto <= 0) {
                JOptionPane.showMessageDialog(vista, "Los valores deben ser mayores a cero.", "Valor inválido", JOptionPane.WARNING_MESSAGE);
                return;
            }


            int stockActual = dao.obtenerStockActual(idProducto);  // Asegúrate de tener este método en tu DAO
            if (cantidad > stockActual) {
                JOptionPane.showMessageDialog(vista, "Stock insuficiente. Disponible: " + stockActual, "Stock insuficiente", JOptionPane.WARNING_MESSAGE);
                return;
            }


            String nombreProducto = dao.obtenerNombreProducto(idProducto);

            DetalleVenta detalle = new DetalleVenta(0, idProducto, cantidad, nombreProducto, precio, totalProducto);
            listaDetalles.add(detalle);

            vista.modTablaVenta.addRow(new Object[]{
                    idProducto, nombreProducto, cantidad, String.format("%.2f", precio), String.format("%.2f", totalProducto)
            });

            if (!listaDetalles.isEmpty()) {
                vista.finalizarVenta.setEnabled(true);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, "Formato inválido: " + ex.getMessage());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(vista, "Error al obtener nombre del producto: " + ex.getMessage());
        }
    }


    private void confirmarVenta() {
        try {
            String nombre = vista.getNombreCliente().getText().trim();
            String telefono = vista.getTelefono().getText().trim();
            String direccion = vista.getDireccion().getText().trim();

            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Debe ingresar el nombre del cliente.", "Cliente requerido", JOptionPane.WARNING_MESSAGE);
                return;
            }

            this.clienteNombreActual = nombre;
            ClienteDAO clienteDAO = new ClienteDAO();
            int clienteId = clienteDAO.obtenerIdOCrear(nombre, telefono, direccion);

            double total = listaDetalles.stream().mapToDouble(DetalleVenta::getTotalProducto).sum();
            Venta venta = new Venta(0, vista.FechaVenta.getText(), total, idEmpleadoActual);

            EmpleadoDAO empleadoDAO = new EmpleadoDAO();
            this.nombreEmpleadoActual = empleadoDAO.obtenerNombreEmpleado(idEmpleadoActual);

            int idVenta = dao.insertarVentaCliente(venta, listaDetalles, clienteId);
            venta.setIdVenta(idVenta);
            for (DetalleVenta detalle : listaDetalles) {
                detalle.setIdVenta(idVenta);
            }


            dao.insertarDetalles(listaDetalles);
            generarFacturaPDF(venta, listaDetalles);

            String fecha = vista.FechaVenta.getText();
            String tipoMovimiento = "Ajuste negativo inventario";
            String nroDocumento = "VENTA-" + venta.getIdVenta();

            for (DetalleVenta detalle : listaDetalles) {
                ModeloMovimiento movimiento = new ModeloMovimiento();
                movimiento.setCantidad(detalle.getCantidad());
                movimiento.setFechaMovimiento(fecha);
                movimiento.setObservacion("Venta");
                movimiento.setProductoId(Integer.parseInt(detalle.getIdProducto()));
                movimiento.setTipoMovimiento(tipoMovimiento);

                boolean exito = movimientoDAO.registrarMovimiento(movimiento, nroDocumento);
                if (!exito) {
                    JOptionPane.showMessageDialog(vista, "Error al registrar salida en inventario del producto: " + detalle.getDescripcion());
                }
            }

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
            File carpeta = new File("facturas");
            if (!carpeta.exists()) carpeta.mkdirs();

            String nombreArchivo = "facturas/Factura_PostresMariaJose_" + venta.getIdVenta() + ".pdf";
            PdfWriter.getInstance(document, new FileOutputStream(nombreArchivo));
            document.open();

            Font tituloFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Font textoFont = FontFactory.getFont(FontFactory.HELVETICA, 12);

            document.add(new Paragraph("POSTRES MARIA JOSE", tituloFont));
            document.add(new Paragraph("Factura N°: " + venta.getIdVenta(), textoFont));
            document.add(new Paragraph("Fecha: " + venta.getFecha(), textoFont));
            document.add(new Paragraph("Cliente: " + clienteNombreActual, textoFont));
            document.add(new Paragraph("Empleado: " + nombreEmpleadoActual, textoFont));
            document.add(new Paragraph("\n"));

            document.add(new Paragraph("Detalles de la venta:", textoFont));
            document.add(new Paragraph("--------------------------------------", textoFont));

            for (DetalleVenta d : detalles) {
                document.add(new Paragraph("Producto: " + d.getDescripcion(), textoFont));
                document.add(new Paragraph("ID Producto: " + d.getIdProducto(), textoFont));
                document.add(new Paragraph("Cantidad: " + d.getCantidad(), textoFont));
                document.add(new Paragraph("Precio Unitario: $" + d.getPrecioUnitario(), textoFont));
                document.add(new Paragraph("Total: $" + d.getTotalProducto(), textoFont));
                document.add(new Paragraph(" ", textoFont));
            }

            document.add(new Paragraph("--------------------------------------", textoFont));
            document.add(new Paragraph("Total a pagar: $" + venta.getTotal(), tituloFont));

            document.add(new Paragraph("\nGracias por su compra. ¡Vuelva pronto!", textoFont));

            document.close();
            Desktop.getDesktop().open(new File(nombreArchivo));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error al generar factura: " + e.getMessage());
        }
    }

    private void regresar() {
        vista.dispose();
        emVista.setVisible(true);
    }

}