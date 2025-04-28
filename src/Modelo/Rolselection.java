package Modelo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Rolselection extends JFrame{
    private JTextField idText;
    private JTextField rolText;
    private JTextField nameText;
    private JTable tabla;
    private JButton button1;
    private JButton consultButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JPanel rolPanel;
    Connection conexion;
    String[] columnas = {"Id","Nombre","Apellido","Rol", "Usuario"};
    String[] registros = new String[10];
    DefaultTableModel modTabla = new DefaultTableModel(null , columnas);
    Statement st;
    ResultSet rs;
    PreparedStatement ps;

    public Rolselection() {


        consultButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    consultar();
                }catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    updateSection();
                }catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    deleteSection();
                }catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    public void mostrarRolSelection() {
        Rolselection mostrar = new Rolselection();
        mostrar.setContentPane(mostrar.rolPanel);
        mostrar.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mostrar.setVisible(true);
        mostrar.pack();

    }

    void conectar() {
        try {
        conexion= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/PostresMariaJose", "root", "Santi104");

        }catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    void consultar() throws SQLException {
        conectar();
        modTabla.setRowCount(0);
        tabla.setModel(modTabla);
        st = conexion.createStatement();
        rs = st.executeQuery("select id, Nombre, Apellido, tipo, Usuario.Usuario from usuarios");
        while (rs.next()) {
            registros[0] = rs.getString("id");
            registros[1] = rs.getString("nombre");
            registros[2] = rs.getString("apellido");
            registros[3] = rs.getString("tipo");
            registros[4] = rs.getString("usuario");
            modTabla.addRow(registros);
        }
    }

    void updateSection() throws SQLException {
        ps = conexion.prepareStatement("Update Usuarios set tipo=? where id=?");
        ps.setString(1, rolText.getText());
        ps.setInt(2, Integer.parseInt(idText.getText()));

        if (ps.executeUpdate() > 0) {
            idText.setText("");
            rolText.setText("");
            nameText.setText("");
            consultar();
        }
    }

    void deleteSection() throws SQLException {
        ps = conexion.prepareStatement("Delete from Usuarios where id=?");
        ps.setInt(1, Integer.parseInt(idText.getText()));

        if (ps.executeUpdate() > 0) {
            idText.setText("");
            rolText.setText("");
            nameText.setText("");
            consultar();
        }
    }

}