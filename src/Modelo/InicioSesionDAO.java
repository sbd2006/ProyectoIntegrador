package Modelo;

import Vista.VistaIS;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InicioSesionDAO {
    Conexion conX = new Conexion();
    private ResultSet rs;
    private PreparedStatement ps;
    private VistaIS vista;
    private String nombreUsuario;

    public InicioSesionDAO(VistaIS vista) {
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
                nombreUsuario = rs.getString("Nombre");
                return rs.getString("tipo");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
        return null; // Si no encuentra nada
    }
    public String getUserName() {
        return nombreUsuario;
    }

}
