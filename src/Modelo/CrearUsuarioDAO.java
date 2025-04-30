package Modelo;

import java.sql.*;

public class CrearUsuarioDAO {
    private final String url = "jdbc:mysql://127.0.0.1:3306/postresmariajose";
    private final String user = "root";
    private final String pass = "Juanguis-2006";

    public boolean guardarUsuario(CrearUsuario u) {
        if (existeUsuario(u.getUsuario())) {
            System.err.println("Usuario ya existe en la base de datos");
            return false;
        }

        try (Connection con = DriverManager.getConnection(url, user, pass)) {
            String sql = "INSERT INTO usuarios (Nombre, Apellido, Telefono, Direccion, Usuario, Pass) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, u.getNombre());
            ps.setString(2, u.getApellido());
            ps.setString(3, u.getTelefono());
            ps.setString(4, u.getDireccion());
            ps.setString(5, u.getUsuario());
            ps.setString(6, u.getContraseña());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al guardar usuario: " + e.getMessage());
            return false;
        }
    }

    public boolean existeUsuario(String nombreUsuario) {
        String query = "SELECT COUNT(*) FROM usuarios WHERE usuario = ?";
        try (Connection con = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, nombreUsuario);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error al validar existencia de usuario: " + e.getMessage());
        }
        return false;
    }
}
