package Controlador;

import Modelo.ReporteVentaDAO;
import Vista.ReporteVentaVista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class ReporteVentaControlador {
    private final ReporteVentaVista vista;
    private final ReporteVentaDAO dao;
    private final DecimalFormat formatoMiles = new DecimalFormat("###,###");

    public ReporteVentaControlador(ReporteVentaVista vista, ReporteVentaDAO dao) {
        this.vista = vista;
        this.dao = dao;
        initController();
    }

    private void initController() {
        vista.getGenerarReporteButton().addActionListener(e -> generarReporte());
        vista.getExportarPdfButton().addActionListener(e -> exportarReportePDF());
    }

    private void generarReporte() {
        Date fechaInicioDate = vista.getFechaInicioChooser().getDate();
        Date fechaFinDate = vista.getFechaFinChooser().getDate();
        String tipo = vista.getTipoComboBox().getSelectedItem().toString();

        if (fechaInicioDate == null || fechaFinDate == null) {
            JOptionPane.showMessageDialog(vista, "Por favor, selecciona ambas fechas.");
            return;
        }

        if (fechaInicioDate.after(fechaFinDate)) {
            JOptionPane.showMessageDialog(vista, "La fecha de inicio no puede ser posterior a la de fin.");
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fechaInicio = sdf.format(fechaInicioDate) + " 00:00:00";
        String fechaFin = sdf.format(fechaFinDate) + " 23:59:59";

        try {
            List<String[]> resultados = dao.obtenerVentasPorRangoConPago(fechaInicio, fechaFin, tipo);

            DefaultTableModel modeloTabla = new DefaultTableModel(
                    new String[]{"Periodo", "Método de Pago", "N° ventas", "Total"}, 0
            ) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            for (String[] fila : resultados) {
                fila[3] = "$" + formatoMiles.format(Double.parseDouble(fila[3]));
                modeloTabla.addRow(fila);
            }

            vista.getTablaResultados().setModel(modeloTabla);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(vista, "Error al obtener datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void exportarReportePDF() {
        Date fechaInicioDate = vista.getFechaInicioChooser().getDate();
        Date fechaFinDate = vista.getFechaFinChooser().getDate();
        String tipo = vista.getTipoComboBox().getSelectedItem().toString().toLowerCase();

        if (fechaInicioDate == null || fechaFinDate == null) {
            JOptionPane.showMessageDialog(vista, "Por favor, selecciona ambas fechas.");
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fechaInicio = sdf.format(fechaInicioDate) + " 00:00:00";
        String fechaFin = sdf.format(fechaFinDate) + " 23:59:59";

        try {
            File folder = new File("Reportes venta");
            if (!folder.exists()) folder.mkdirs();

            String fechaInicioNombre = fechaInicio.split(" ")[0];
            String fechaFinNombre = fechaFin.split(" ")[0];
            File pdfFile = new File(folder, "ReporteVentas_" + tipo + "_" + fechaInicioNombre + "_a_" + fechaFinNombre + ".pdf");

            Document document = new Document(PageSize.A3.rotate());
            PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
            document.open();

            Font tituloFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Font subtituloFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
            Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 10);

            Paragraph titulo = new Paragraph("Reporte de Ventas - " + tipo.substring(0, 1).toUpperCase() + tipo.substring(1), tituloFont);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);
            document.add(new Paragraph("Rango: " + fechaInicio + " a " + fechaFin, normalFont));
            document.add(new Paragraph("Generado: " + new Date(), normalFont));
            document.add(Chunk.NEWLINE);

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);
            table.addCell(new PdfPCell(new Phrase("Periodo", subtituloFont)));
            table.addCell(new PdfPCell(new Phrase("Método de Pago", subtituloFont)));
            table.addCell(new PdfPCell(new Phrase("N° ventas", subtituloFont)));
            table.addCell(new PdfPCell(new Phrase("Total", subtituloFont)));

            JTable tabla = vista.getTablaResultados();
            for (int row = 0; row < tabla.getRowCount(); row++) {
                for (int col = 0; col < tabla.getColumnCount(); col++) {
                    Object value = tabla.getValueAt(row, col);
                    String cellValue = value != null ? value.toString() : "";
                    if (col == 3) {
                        try {
                            double numero = Double.parseDouble(cellValue);
                            cellValue = "$" + formatoMiles.format(numero);
                        } catch (NumberFormatException ignored) {}
                    }
                    table.addCell(new PdfPCell(new Phrase(cellValue, normalFont)));
                }
            }
            document.add(table);

            document.add(new Paragraph("Totales por Método de Pago:", subtituloFont));
            document.add(Chunk.NEWLINE);
            PdfPTable metodoPagoTabla = new PdfPTable(2);
            metodoPagoTabla.setWidthPercentage(60);

            Font negrilla = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 13);

            metodoPagoTabla.addCell(new PdfPCell(new Phrase("Método", negrilla)));
            metodoPagoTabla.addCell(new PdfPCell(new Phrase("Total", negrilla)));


            Map<String, Double> totales = dao.obtenerTotalesPorMetodoPago(fechaInicio, fechaFin);
            double sumaTotal = 0;
            for (Map.Entry<String, Double> entry : totales.entrySet()) {
                metodoPagoTabla.addCell(entry.getKey());
                metodoPagoTabla.addCell("$" + formatoMiles.format(entry.getValue()));
                sumaTotal += entry.getValue();
            }
            document.add(metodoPagoTabla);
            document.add(Chunk.NEWLINE);
            Paragraph totalGlobal = new Paragraph("Total General: $" + formatoMiles.format(sumaTotal), subtituloFont);
            totalGlobal.setSpacingBefore(10);
            totalGlobal.setAlignment(Element.ALIGN_RIGHT);
            document.add(totalGlobal);

            int confirmar = JOptionPane.showConfirmDialog(vista, "¿Deseas agregar el top 5 de productos más y menos vendidos?", "Confirmación", JOptionPane.YES_NO_OPTION);
            if (confirmar == JOptionPane.YES_OPTION) {
                document.add(Chunk.NEWLINE);
                document.add(Chunk.NEWLINE);
                document.add(new Paragraph("Top 5 productos más vendidos:", subtituloFont));
                document.add(Chunk.NEWLINE);
                PdfPTable topMasVendidos = new PdfPTable(3);
                topMasVendidos.setWidthPercentage(100);
                topMasVendidos.addCell(new PdfPCell(new Phrase("ID Producto", subtituloFont)));
                topMasVendidos.addCell(new PdfPCell(new Phrase("Nombre", subtituloFont)));
                topMasVendidos.addCell(new PdfPCell(new Phrase("Cantidad Vendida", subtituloFont)));
                List<String[]> top5 = dao.obtenerTopProductos(fechaInicio, fechaFin, true);
                for (String[] fila : top5) {
                    for (String celda : fila) {
                        topMasVendidos.addCell(new PdfPCell(new Phrase(celda, normalFont)));
                    }
                }
                document.add(topMasVendidos);

                document.add(Chunk.NEWLINE);
                document.add(Chunk.NEWLINE);
                document.add(new Paragraph("Top 5 productos menos vendidos:", subtituloFont));
                document.add(Chunk.NEWLINE);
                PdfPTable topMenosVendidos = new PdfPTable(3);
                topMenosVendidos.setWidthPercentage(100);
                topMenosVendidos.addCell(new PdfPCell(new Phrase("ID Producto", subtituloFont)));
                topMenosVendidos.addCell(new PdfPCell(new Phrase("Nombre", subtituloFont)));
                topMenosVendidos.addCell(new PdfPCell(new Phrase("Cantidad Vendida", subtituloFont)));
                List<String[]> bottom5 = dao.obtenerTopProductos(fechaInicio, fechaFin, false);
                for (String[] fila : bottom5) {
                    for (String celda : fila) {
                        topMenosVendidos.addCell(new PdfPCell(new Phrase(celda, normalFont)));
                    }
                }
                document.add(topMenosVendidos);
            }

            document.close();
            Desktop.getDesktop().open(pdfFile);
            JOptionPane.showMessageDialog(vista, "Reporte exportado y abierto correctamente.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error exportando PDF: " + ex.getMessage());
        }
    }

}