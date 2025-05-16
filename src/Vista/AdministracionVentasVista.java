package Vista;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
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

        JPanel panelPrincipal = new JPanel(new BorderLayout());


        JLabel tituloLabel = new JLabel("Consultar Ventas", JLabel.CENTER);
        tituloLabel.setFont(new Font("Britannic Bold", Font.PLAIN, 18));
        panelPrincipal.add(tituloLabel, BorderLayout.NORTH);

        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));

        selectorFecha = new JDateChooser();
        selectorFecha.setDateFormatString("yyyy-MM-dd");

        consultarButton = new JButton("Consultar");
        regresarButton = new JButton("Regresar");

        Font botonFuente = new Font("Britannic Bold", Font.PLAIN, 16);
        consultarButton.setFont(botonFuente);
        regresarButton.setFont(botonFuente);

        panelSuperior.add(new JLabel("Fecha:"));
        panelSuperior.add(selectorFecha);
        panelSuperior.add(consultarButton);
        panelSuperior.add(regresarButton);

        JPanel panelTop = new JPanel(new BorderLayout());
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

        JScrollPane scrollPane = new JScrollPane(table1);
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
