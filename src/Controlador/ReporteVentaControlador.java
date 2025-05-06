package Controlador;

import Modelo.ReporteVentaDAO;
import Vista.ReporteVentaVista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.File;
import java.io.FileOutputStream;


import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfPTable;


public class ReporteVentaControlador {
    private final ReporteVentaVista vista;
    private final ReporteVentaDAO dao;

    public ReporteVentaControlador(ReporteVentaVista vista, ReporteVentaDAO dao) {
        this.vista = vista;
        this.dao = dao;
        initController();
    }

    private void initController() {
        vista.getGenerarReporteButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generarReporte();
            }
        });

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
            DefaultTableModel modeloTabla = new DefaultTableModel();
            modeloTabla.setColumnIdentifiers(new String[]{"Periodo", "Cantidad", "Total"});

            for (String[] fila : resultados) {
                modeloTabla.addRow(fila);
            }

            vista.getTablaResultados().setModel(modeloTabla);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(vista, "Error al obtener datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }



    public void iniciarVista() {
        vista.setVisible(true);
    }

    private void exportarReportePDF() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Reporte como PDF");
        int userSelection = fileChooser.showSaveDialog(vista);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            if (!fileToSave.getAbsolutePath().endsWith(".pdf")) {
                fileToSave = new File(fileToSave.getAbsolutePath() + ".pdf");
            }

            try (FileOutputStream fos = new FileOutputStream(fileToSave)) {
                Document document = new Document();
                PdfWriter.getInstance(document, fos);
                document.open();
                document.add(new Paragraph("Reporte de Ventas"));
                document.add(new Paragraph("Fecha: " + new Date().toString()));
                document.add(Chunk.NEWLINE);

                PdfPTable table = new PdfPTable(3);
                table.addCell("Periodo");
                table.addCell("Cantidad");
                table.addCell("Total");

                JTable tabla = vista.getTablaResultados();
                for (int row = 0; row < tabla.getRowCount(); row++) {
                    for (int col = 0; col < tabla.getColumnCount(); col++) {
                        Object value = tabla.getValueAt(row, col);
                        table.addCell(value != null ? value.toString() : "");
                    }
                }

                document.add(table);
                document.close();
                JOptionPane.showMessageDialog(vista, "Reporte exportado correctamente.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(vista, "Error exportando PDF: " + ex.getMessage());
            }
        }
    }

}