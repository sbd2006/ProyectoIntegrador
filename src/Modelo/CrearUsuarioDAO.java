package Modelo;

import javax.swing.*;
import java.sql.*;

public class CrearUsuarioDAO {

    private final String URL = "jdbc:mysql://127.0.0.1:3306/postresmariajose";
    private final String USER = "root";
    private final String PASSWORD = "Santi104";

    public boolean guardarUsuario(CrearUsuario usuario) {
        try (Connection conexion = DriverManager.getConnection(URL, USER, PASSWORD)) {
             CallableStatement cs = conexion.prepareCall("{CALL guardarUsuarios(?,?,?,?,?,?)}");

            cs.setString(1, usuario.getNombre());
            cs.setString(2, usuario.getApellido());
            cs.setString(3, usuario.getTelefono());
            cs.setString(4, usuario.getDireccion());
            cs.setString(5, usuario.getUsuario());
            cs.setString(6, usuario.getContrasena());

            cs.executeUpdate();
            return true;
        } catch (SQLIntegrityConstraintViolationException e) {
            JOptionPane.showMessageDialog(null, "El usuario ya existe. Por favor use otro nombre de usuario.");
            return false;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar el usuario: " + e.getMessage());
            return false;
        }
    }
}
