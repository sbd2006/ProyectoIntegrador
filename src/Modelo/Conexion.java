package Modelo;

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
            conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/PostresMariaJose", "root", "Santi104");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
