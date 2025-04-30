package Modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminVentaDAO {

    private final String url = "jdbc:mysql://localhost:3306/tu_base";
    private final String usuario = "root";
    private final String contraseña = "Santi104";

    public List<String[]> consultarPorFecha(String fecha) {
        List<String[]> resultados = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(url, usuario, contraseña);
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM tu_tabla WHERE fecha = ?")) {

            stmt.setString(1, fecha);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                resultados.add(new String[]{
                        String.valueOf(rs.getInt("id")),
                        rs.getString("nombre"),
                        String.valueOf(rs.getDate("fecha"))
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultados;
    }
}
