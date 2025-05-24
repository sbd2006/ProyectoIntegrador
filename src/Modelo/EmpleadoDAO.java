package Modelo;

import java.sql.*;


public class EmpleadoDAO {

    private final String URL = "jdbc:mysql://127.0.0.1:3306/PostresMariaJose";
    private final String USER = "root";
    private final String PASSWORD = "Juanguis-2006";


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



}
