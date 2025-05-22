package Modelo;

import Vista.VistaIS;

import javax.swing.*;
import java.sql.*;

public class ModeloIS {
    private VistaIS vista;
    private ResultSet rs;
    private PreparedStatement ps;
    private CallableStatement cs;
    private String nombreUsuario;
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
            String sql = "{CALL validacionSQL (?,?)}";

            cs = conX.getConexion().prepareCall(sql);
            cs.setString(1, user);
            cs.setString(2, pass);

            rs = cs.executeQuery();

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
