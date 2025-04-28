package Modelo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class CrearUsuarioDAO {
    public void insertarUsuario(CrearUsuario u) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/?user=root", "root", "OH{c<6H1#cQ%F69$i")) {
            String sql = "INSERT INTO usuarios (nombre, apellido, telefono, direccion, usuario, contraseña) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, u.getNombre());
            ps.setString(2, u.getApellido());
            ps.setString(3, u.getTelefono());
            ps.setString(4, u.getDireccion());
            ps.setString(5, u.getUsuario());
            ps.setString(6, u.getContraseña());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
