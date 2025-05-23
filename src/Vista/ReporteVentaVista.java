package Vista;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
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
        setSize(1920, 1080);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);


        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.decode("#FFF5E6"));
        JPanel formularioPanel = new JPanel(new GridLayout(2, 5, 10, 10));
        formularioPanel.setBackground(Color.decode("#FFF5E6"));

        fechaInicioChooser = new JDateChooser();
        fechaInicioChooser.setDateFormatString("yyyy-MM-dd");

        fechaFinChooser = new JDateChooser();
        fechaFinChooser.setDateFormatString("yyyy-MM-dd");

        tipoComboBox = new JComboBox<>(new String[]{"Diario", "Semanal", "Mensual"});
        generarReporteButton = new JButton("Generar Reporte");
        exportarPdfButton = new JButton("Exportar PDF");

        generarReporteButton.setBackground(Color.decode("#F8C8DC"));
        generarReporteButton.setForeground(Color.decode("#6E5952"));
        exportarPdfButton.setBackground(Color.decode("#8B5E3C"));
        exportarPdfButton.setForeground(Color.decode("#FFFFFF"));

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
        tablaResultados.setBackground(Color.decode("#FFFFFF"));
        tablaResultados.setForeground(Color.decode("#6E5952"));
        tablaResultados.setSelectionBackground(Color.decode("#F8C8DC"));
        tablaResultados.setSelectionForeground(Color.decode("#FFFFFF"));
        tablaResultados.getTableHeader().setBackground(Color.decode("#FFF5E6"));
        tablaResultados.getTableHeader().setForeground(Color.decode("#6E5952"));

        JScrollPane scrollPane = new JScrollPane(tablaResultados);
        scrollPane.getViewport().setBackground(Color.decode("#FFFFFF"));
        scrollPane.setBackground(Color.decode("#FFF5E6"));
        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                thumbColor = Color.decode("#F8C8DC");
                trackColor = Color.decode("#DDD2CC");
            }
        });

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
