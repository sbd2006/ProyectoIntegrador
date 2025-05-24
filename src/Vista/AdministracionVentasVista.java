package Vista;

import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdministracionVentasVista extends JFrame {
    public JButton consultarButton;
    public JButton regresarButton;
    public JTable table1;
    public DefaultTableModel modeloTabla;
    public JDateChooser selectorFecha;
    private JFrame ventanaAnterior;

    public AdministracionVentasVista(JFrame ventanaAnterior) {
        this.ventanaAnterior = ventanaAnterior;

        setTitle("Consulta por Fecha");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 450);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);


        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setOpaque(true);
        panelPrincipal.setBackground(Color.decode("#FFF5E6"));




        JLabel tituloLabel = new JLabel("Consultar Ventas", JLabel.CENTER);
        tituloLabel.setFont(new Font("Britannic Bold", Font.PLAIN, 18));
        panelPrincipal.add(tituloLabel, BorderLayout.NORTH);

        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSuperior.setOpaque(true);
        panelSuperior.setBackground(Color.decode("#FFF5E6"));

        selectorFecha = new JDateChooser();
        selectorFecha.setDateFormatString("yyyy-MM-dd");

        consultarButton = new JButton("Consultar");
        regresarButton = new JButton("Regresar");

        Font botonFuente = new Font("Britannic Bold", Font.PLAIN, 16);
        consultarButton.setFont(botonFuente);
        regresarButton.setFont(botonFuente);

        consultarButton.setBackground(Color.decode("#F8C8DC"));
        consultarButton.setForeground(Color.decode("#FFFFFF"));
        regresarButton.setBackground(Color.decode("#8B5E3C"));
        regresarButton.setForeground(Color.decode("#FFFFFF"));

        panelSuperior.add(new JLabel("Fecha:"));
        panelSuperior.add(selectorFecha);
        panelSuperior.add(consultarButton);
        panelSuperior.add(regresarButton);

        JPanel panelTop = new JPanel(new BorderLayout());
        panelTop.setOpaque(true);
        panelTop.setBackground(Color.decode("#FFF5E6"));

        panelTop.add(tituloLabel, BorderLayout.NORTH);
        panelTop.add(panelSuperior, BorderLayout.SOUTH);
        panelPrincipal.add(panelTop, BorderLayout.NORTH);

        table1 = new JTable();
        modeloTabla = new DefaultTableModel(new String[]{"ID VENTA", "FECHA", "PRODUCTOS Y CANTIDADES", "TOTAL VENTA"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table1.setModel(modeloTabla);

        table1.setBackground(Color.decode("#FFFFFF"));
        table1.setForeground(Color.decode("#6E5952"));
        table1.setSelectionBackground(Color.decode("#F8C8DC"));
        table1.setSelectionForeground(Color.decode("#6E5952"));
        table1.getTableHeader().setBackground(Color.decode("#FFF5E6"));
        table1.getTableHeader().setForeground(Color.decode("#6E5952"));

        JScrollPane scrollPane = new JScrollPane(table1);
        scrollPane.getViewport().setBackground(Color.decode("#FFFFFF"));
        scrollPane.setBackground(Color.decode("#FFF5E6"));
        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                thumbColor = Color.decode("#F8C8DC");
                trackColor = Color.decode("#DDD2CC");
            }
        });

        panelPrincipal.add(scrollPane, BorderLayout.CENTER);

        setContentPane(panelPrincipal);

        regresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cerrarYRegresar();
            }
        });
    }

    private void cerrarYRegresar() {
        this.dispose();
        if (ventanaAnterior != null) {
            ventanaAnterior.setVisible(true);
        }
    }

    public void mostrarVista() {
        setVisible(true);
    }
}
