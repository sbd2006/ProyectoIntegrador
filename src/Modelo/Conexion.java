package Modelo;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private Connection conexion;

    public Connection getConexion() {
        return conexion;
    }

    public void conectar() {
        try {
            conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/postresmariajose", "root", "OH{c<6H1#cQ%F69$i");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"No se pudo hacer la conexion "+ e);
            throw new RuntimeException(e);
        }
    }
}
