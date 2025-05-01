package Modelo;

import javax.swing.*;
import java.sql.*;

public class CrearUsuarioDAO {


    private final String URL = "jdbc:mysql://127.0.0.1:3306/postresmariajose";
    private final String USER = "root";
    private final String PASSWORD = "Juanguis-2006";

    public boolean guardarUsuario(CrearUsuario usuario) {
        String sql = "INSERT INTO usuarios (Nombre, Apellido, Telefono, Direccion, Usuario, Pass) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conexion = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setString(3, usuario.getTelefono());
            ps.setString(4, usuario.getDireccion());
            ps.setString(5, usuario.getUsuario());
            ps.setString(6, usuario.getContrasena());

            ps.executeUpdate();
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