package Modelo;

import Vista.VistaIS;

import javax.swing.*;
import java.sql.*;

public class ModeloIS {
    private VistaIS vista;
    private ResultSet rs;
    private PreparedStatement ps;
    private String nombreUsuario; // ← Aquí se guarda el nombre del usuario
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
            String sql = "SELECT * FROM usuarios WHERE Usuario = ? AND Pass = ?";
            ps = conX.getConexion().prepareStatement(sql);
            ps.setString(1, user);
            ps.setString(2, pass);

            rs = ps.executeQuery();

            if (rs.next()) {
                // Guardar el nombre para usarlo en la bienvenida
                nombreUsuario = rs.getString("Nombre"); // Asegúrate de que la columna se llame así
                return rs.getString("tipo"); // Retorna el tipo (Administrador / Usuario)
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }

        return null; // Si no encuentra nada
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }
}
