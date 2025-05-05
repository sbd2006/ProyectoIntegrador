package Vista;

import javax.swing.*;
import java.awt.*;
import com.toedter.calendar.JDateChooser;

public class ReporteVentaVista extends JFrame {

    private final JDateChooser fechaInicioChooser;
    private final JDateChooser fechaFinChooser;
    private final JComboBox<String> tipoComboBox;
    private final JButton generarReporteButton;
    private final JButton exportarPdfButton;
    private final JTable tablaResultados;

    public ReporteVentaVista() {
        setTitle("Reporte de Ventas");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());
        JPanel formularioPanel = new JPanel(new GridLayout(2, 5, 10, 10));

        fechaInicioChooser = new JDateChooser();
        fechaInicioChooser.setDateFormatString("yyyy-MM-dd");

        fechaFinChooser = new JDateChooser();
        fechaFinChooser.setDateFormatString("yyyy-MM-dd");

        tipoComboBox = new JComboBox<>(new String[]{"Diario", "Semanal", "Mensual"});
        generarReporteButton = new JButton("Generar Reporte");
        exportarPdfButton = new JButton("Exportar PDF");

        formularioPanel.add(new JLabel("Fecha Inicio:"));
        formularioPanel.add(fechaInicioChooser);
        formularioPanel.add(new JLabel("Fecha Fin:"));
        formularioPanel.add(fechaFinChooser);
        formularioPanel.add(new JLabel("Tipo:"));
        formularioPanel.add(tipoComboBox);
        formularioPanel.add(generarReporteButton);
        formularioPanel.add(exportarPdfButton);

        panel.add(formularioPanel, BorderLayout.NORTH);

        tablaResultados = new JTable();
        JScrollPane scrollPane = new JScrollPane(tablaResultados);
        panel.add(scrollPane, BorderLayout.CENTER);

        add(panel);
    }

    public JDateChooser getFechaInicioChooser() {
        return fechaInicioChooser;
    }

    public JDateChooser getFechaFinChooser() {
        return fechaFinChooser;
    }

    public JComboBox<String> getTipoComboBox() {
        return tipoComboBox;
    }

    public JButton getGenerarReporteButton() {
        return generarReporteButton;
    }

    public JButton getExportarPdfButton() {
        return exportarPdfButton;
    }

    public JTable getTablaResultados() {
        return tablaResultados;
    }
}
