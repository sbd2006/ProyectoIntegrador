package Modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import Vista.VistaIS;

import javax.swing.*;

public class ModeloIS {
    private VistaIS vista;
    private ResultSet rs;
    private Statement st;
    private PreparedStatement ps;

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
        Conexion conX = new Conexion();
        conX.conectar();
        String user = getUser().getText();
        String pass = String.valueOf(getPass().getPassword());

        try {
            st = conX.getConexion().createStatement();
            rs = st.executeQuery("SELECT * FROM usuarios WHERE Usuario = '" + user + "' AND Pass = '" + pass + "'");

            if (rs.next()) {
                return rs.getString("tipo");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
        return null;
    }

}
