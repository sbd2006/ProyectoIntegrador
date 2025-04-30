package Vista;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AdministracionVentasVista extends JFrame {
    public JPanel panel1;
    public JButton consultarButton;
    public JTable table1;
    public JDateChooser selectorFecha;
    public DefaultTableModel modeloTabla;

    public AdministracionVentasVista() {
        setTitle("Consulta por Fecha");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setContentPane(panel1);

        selectorFecha = new JDateChooser();
        selectorFecha.setDateFormatString("yyyy-MM-dd");

        panel1.add(new JLabel("Selecciona la fecha:"));
        panel1.add(selectorFecha);

        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre", "Fecha"}, 0);
        table1.setModel(modeloTabla);
    }

    public void mostrarVista() {
        setVisible(true);
    }
}
