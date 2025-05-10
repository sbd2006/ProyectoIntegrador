package Modelo;

import java.sql.*;
import java.util.*;

public class ReporteVentaDAO {
    private final String URL = "jdbc:mysql://127.0.0.1:3306/PostresMariaJose";
    private final String USER = "root";
    private final String PASSWORD = "Juanguis-2006";

    public List<String[]> obtenerVentasPorRango(String fechaInicio, String fechaFin, String tipo) throws SQLException {
        String agrupamiento;
        switch (tipo.toLowerCase()) {
            case "diario": agrupamiento = "DATE(v.FECHA_VENTA)"; break;
            case "semanal": agrupamiento = "YEARWEEK(v.FECHA_VENTA)"; break;
            case "mensual": agrupamiento = "DATE_FORMAT(v.FECHA_VENTA, '%Y-%m')"; break;
            default: agrupamiento = "DATE(v.FECHA_VENTA)";
        }

        String sql = "SELECT " + agrupamiento + " AS periodo, COUNT(*) AS cantidad, SUM(v.TOTAL) AS total " +
                "FROM venta v WHERE v.FECHA_VENTA BETWEEN ? AND ? GROUP BY periodo ORDER BY periodo";

        List<String[]> resultados = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, fechaInicio);
            ps.setString(2, fechaFin);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                resultados.add(new String[] {
                        rs.getString("periodo"),
                        rs.getString("cantidad"),
                        rs.getString("total")
                });
            }
        }
        return resultados;
    }

    public List<String[]> obtenerTopProductos(String fechaInicio, String fechaFin, boolean mayores) throws SQLException {
        String orden = mayores ? "DESC" : "ASC";
        String sql = "SELECT d.ID_PRODUCTO, p.Nombre, SUM(d.CANTIDAD_PRODUCTO) as total " +
                "FROM detalle_venta d JOIN producto p ON d.ID_PRODUCTO = p.ID_PRODUCTO " +
                "JOIN venta v ON d.ID_VENTA = v.ID_VENTA " +
                "WHERE v.FECHA_VENTA BETWEEN ? AND ? " +
                "GROUP BY d.ID_PRODUCTO " +
                "ORDER BY total " + orden + " LIMIT 5";

        List<String[]> productos = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, fechaInicio);
            ps.setString(2, fechaFin);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                productos.add(new String[] {
                        rs.getString("ID_PRODUCTO"),
                        rs.getString("Nombre"),
                        rs.getString("total")
                });
            }
        }
        return productos;
    }
}
