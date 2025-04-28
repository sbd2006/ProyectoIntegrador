package Vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import com.toedter.calendar.JDateChooser;

import java.sql.*;
import java.text.SimpleDateFormat;

public class AdministracionVentas extends JFrame {
    private JPanel panel1;
    private JButton consultarButton;
    private JTable table1;


    private DefaultTableModel modeloTabla;
    private JDateChooser selectorFecha;

    public AdministracionVentas() {
        setContentPane(panel1); // <- Esto conecta el diseño con el JFrame
        setTitle("Consulta por Fecha");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Inicializar manualmente JDateChooser
        selectorFecha = new JDateChooser();
        selectorFecha.setDateFormatString("yyyy-MM-dd");

        panel1.add(new JLabel("Selecciona la fecha:"));
        panel1.add(selectorFecha);

        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre", "Fecha"}, 0);
        table1.setModel(modeloTabla);

        consultarButton.addActionListener(e -> consultarDatos());

        setVisible(true);
    }

    private void consultarDatos() {
        modeloTabla.setRowCount(0); // Limpiar la tabla antes de consultar

        java.util.Date fechaSeleccionada = selectorFecha.getDate();
        if (fechaSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "Por favor selecciona una fecha.");
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fecha = sdf.format(fechaSeleccionada);

        // Cambia estos valores por los reales de tu base de datos
        String url = "jdbc:mysql://localhost:3306/tu_base";
        String usuario = "usuario";
        String contraseña = "contraseña";

        try (Connection conn = DriverManager.getConnection(url, usuario, contraseña);
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM tu_tabla WHERE fecha = ?")) {

            stmt.setString(1, fecha);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                modeloTabla.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getDate("fecha")
                });
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al consultar datos.");
        }
    }
}


