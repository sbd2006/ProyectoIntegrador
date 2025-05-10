package Modelo;

import javax.swing.*;
import java.sql.*;

public class CrearUsuarioDAO {

    private final String URL = "jdbc:mysql://127.0.0.1:3306/postresmariajose";
    private final String USER = "root";
    private final String PASSWORD = "Juanguis-2006";

    public boolean guardarUsuario(CrearUsuario usuario) {
        String sqlEmpleado = "INSERT INTO empleado (Nombre, Apellido, Telefono, Direccion) VALUES (?, ?, ?, ?)";
        String sqlUsuario = "INSERT INTO usuarios (Usuario, Pass, tipo, ID_EMPLEADO) VALUES (?, ?, ?, ?)";

        try (Connection conexion = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement psEmpleado = conexion.prepareStatement(sqlEmpleado, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement psUsuario = conexion.prepareStatement(sqlUsuario)) {

            conexion.setAutoCommit(false);

            psEmpleado.setString(1, usuario.getNombre());
            psEmpleado.setString(2, usuario.getApellido());
            psEmpleado.setString(3, usuario.getTelefono());
            psEmpleado.setString(4, usuario.getDireccion());
            psEmpleado.executeUpdate();

            ResultSet rs = psEmpleado.getGeneratedKeys();
            if (!rs.next()) {
                conexion.rollback();
                throw new SQLException("No se pudo obtener ID del empleado");
            }
            int idEmpleado = rs.getInt(1);

            // Insertar usuario con tipo fijo 'Usuario'
            psUsuario.setString(1, usuario.getUsuario());
            psUsuario.setString(2, usuario.getContrasena());
            psUsuario.setString(3, "Usuario"); // ✅ tipo determinado automáticamente
            psUsuario.setInt(4, idEmpleado);
            psUsuario.executeUpdate();

            conexion.commit();
            return true;

        } catch (SQLIntegrityConstraintViolationException e) {
            JOptionPane.showMessageDialog(null, "El usuario ya existe. Por favor use otro nombre de usuario.");
            return false;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar el usuario: " + e.getMessage());
            return false;
        }
    }

}
