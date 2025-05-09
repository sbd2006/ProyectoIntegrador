package Modelo;

import Vista.VistaIS;

import javax.swing.*;
import java.sql.*;

public class ModeloIS {
    private VistaIS vista;
    private ResultSet rs;
    private PreparedStatement ps;
    private String nombreUsuario; // ← Aquí se guarda el nombre del usuario
    private int idEmpleado;
    public int getIdEmpleado() { return idEmpleado; }

    Conexion conX = new Conexion();

    public ModeloIS(VistaIS vista) {
        this.vista = vista;
    }

    public JTextField getUser() {
        return vista.getIngresoUser();
    }

    public JPasswordField getPass() {
        return vista.getIngresoPassword();
    }

    public String validacionSQL() {
        conX.conectar();
        String user = getUser().getText();
        String pass = String.valueOf(getPass().getPassword());


        try {
            String sql = """
                SELECT u.Usuario, u.Pass, u.tipo, e.Nombre, u.ID_EMPLEADO 
                FROM usuario u 
                JOIN empleado e ON u.ID_EMPLEADO = e.ID_EMPLEADO 
                WHERE u.Usuario = ? AND u.Pass = ?
                
            """;


            ps = conX.getConexion().prepareStatement(sql);
            ps.setString(1, user);
            ps.setString(2, pass);

            rs = ps.executeQuery();

            if (rs.next()) {
                nombreUsuario = rs.getString("Nombre");
                this.idEmpleado = rs.getInt("ID_EMPLEADO");
                return rs.getString("tipo");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }

        return null;
    }



    public String getNombreUsuario() {
        return nombreUsuario;
    }
}
