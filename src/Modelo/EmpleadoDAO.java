package Modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAO {

    private final String URL = "jdbc:mysql://127.0.0.1:3306/PostresMariaJose";
    private final String USER = "root";
    private final String PASSWORD = "OH{c<6H1#cQ%F69$i";

    public String obtenerNombreEmpleado(int idEmpleado) {
        String sql = "SELECT Nombre FROM empleado WHERE ID_EMPLEADO = ?";
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idEmpleado);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("Nombre");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Desconocido";
    }

    public List<Empleado> obtenerTodosLosEmpleados() {
        List<Empleado> lista = new ArrayList<>();
        String sql = "SELECT ID_EMPLEADO, Nombre, Apellido, Telefono, Direccion FROM empleado";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Empleado e = new Empleado();
                e.setIdEmpleado(rs.getInt("ID_EMPLEADO"));
                e.setNombre(rs.getString("Nombre"));
                e.setApellido(rs.getString("Apellido"));
                e.setTelefono(rs.getString("Telefono"));
                e.setDireccion(rs.getString("Direccion"));
                lista.add(e);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return lista;
    }

    // Retorna el ID generado o -1 si hubo error
    public int insertarEmpleado(Empleado empleado) {
        String sql = "INSERT INTO empleado (Nombre, Apellido, Telefono, Direccion) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, empleado.getNombre());
            stmt.setString(2, empleado.getApellido());
            stmt.setString(3, empleado.getTelefono());
            stmt.setString(4, empleado.getDireccion());

            int filas = stmt.executeUpdate();
            if (filas > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return -1;
    }
}
