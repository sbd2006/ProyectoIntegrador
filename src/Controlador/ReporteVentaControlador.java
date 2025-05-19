
package Controlador;

import Modelo.ReporteVentaDAO;
import Vista.ReporteVentaVista;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.io.File;
import java.io.FileOutputStream;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPCell;

public class ReporteVentaControlador {
    private final ReporteVentaVista vista;
    private final ReporteVentaDAO dao;

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
        String fechaInicio = sdf.format(fechaInicioDate);
        String fechaFin = sdf.format(fechaFinDate);

        try {
            List<String[]> resultados = dao.obtenerVentasPorRango(fechaInicio, fechaFin, tipo);
            DefaultTableModel modeloTabla = new DefaultTableModel(new String[]{"Periodo", "Cantidad", "Total"}, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            for (String[] fila : resultados) {
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

        if (fechaInicioDate == null || fechaFinDate == null) {
            JOptionPane.showMessageDialog(vista, "Por favor, selecciona ambas fechas.");
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fechaInicio = sdf.format(fechaInicioDate);
        String fechaFin = sdf.format(fechaFinDate);

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Reporte como PDF");
        int userSelection = fileChooser.showSaveDialog(vista);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            if (!fileToSave.getAbsolutePath().endsWith(".pdf")) {
                fileToSave = new File(fileToSave.getAbsolutePath() + ".pdf");
            }

            try (FileOutputStream fos = new FileOutputStream(fileToSave)) {
                Document document = new Document(PageSize.A4.rotate());
                PdfWriter.getInstance(document, fos);
                document.open();

                Font tituloFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
                Font subtituloFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
                Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 10);

                Paragraph titulo = new Paragraph("Reporte de Ventas", tituloFont);
                titulo.setAlignment(Element.ALIGN_CENTER);
                document.add(titulo);
                document.add(new Paragraph("Rango: " + fechaInicio + " a " + fechaFin, normalFont));
                document.add(new Paragraph("Generado: " + new Date(), normalFont));
                document.add(Chunk.NEWLINE);

                PdfPTable table = new PdfPTable(3);
                table.setWidthPercentage(100);
                table.setSpacingBefore(10f);
                table.setSpacingAfter(10f);
                table.addCell(new PdfPCell(new Phrase("Periodo", subtituloFont)));
                table.addCell(new PdfPCell(new Phrase("Cantidad", subtituloFont)));
                table.addCell(new PdfPCell(new Phrase("Total", subtituloFont)));

                JTable tabla = vista.getTablaResultados();
                for (int row = 0; row < tabla.getRowCount(); row++) {
                    for (int col = 0; col < tabla.getColumnCount(); col++) {
                        Object value = tabla.getValueAt(row, col);
                        table.addCell(new PdfPCell(new Phrase(value != null ? value.toString() : "", normalFont)));
                    }
                }
                document.add(table);

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
                        topMasVendidos.addCell(celda);
                    }
                }
                document.add(topMasVendidos);

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
                        topMenosVendidos.addCell(celda);
                    }
                }
                document.add(topMenosVendidos);

                document.close();
                JOptionPane.showMessageDialog(vista, "Reporte exportado correctamente.");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(vista, "Error exportando PDF: " + ex.getMessage());
            }
        }
    }


}
