package Modelo;

import java.sql.*;
import java.util.*;

public class ReporteVentaDAO {
    private final String URL = "jdbc:mysql://127.0.0.1:3306/PostresMariaJose";
    private final String USER = "root";
    private final String PASSWORD = "OH{c<6H1#cQ%F69$i";

    public List<String[]> obtenerVentasPorRangoConPago(String fechaInicio, String fechaFin, String tipo) throws SQLException {
        String agrupamiento;
        switch (tipo.toLowerCase()) {
            case "diario": agrupamiento = "DATE(v.FECHA_VENTA)"; break;
            case "semanal":
                agrupamiento = "CONCAT(DATE_FORMAT(v.FECHA_VENTA - INTERVAL WEEKDAY(v.FECHA_VENTA) DAY, '%Y-%m-%d'), ' a ', DATE_FORMAT(v.FECHA_VENTA + INTERVAL (6 - WEEKDAY(v.FECHA_VENTA)) DAY, '%Y-%m-%d'))";
                break;

            case "mensual": agrupamiento = "DATE_FORMAT(v.FECHA_VENTA, '%Y-%m')"; break;
            default: agrupamiento = "DATE(v.FECHA_VENTA)";
        }

        String sql = "SELECT " + agrupamiento + " AS periodo, v.METODO_PAGO, COUNT(*) AS cantidad, SUM(v.TOTAL) AS total " +
                "FROM venta v WHERE v.FECHA_VENTA BETWEEN ? AND ? " +
                "GROUP BY periodo, v.METODO_PAGO ORDER BY periodo";

        List<String[]> resultados = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, fechaInicio);
            ps.setString(2, fechaFin);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                resultados.add(new String[] {
                        rs.getString("periodo"),
                        rs.getString("METODO_PAGO"),
                        rs.getString("cantidad"),
                        rs.getString("total")
                });
            }
        }
        return resultados;
    }

    public Map<String, Double> obtenerTotalesPorMetodoPago(String fechaInicio, String fechaFin) throws SQLException {
        String sql = "SELECT METODO_PAGO, SUM(TOTAL) as total FROM venta WHERE FECHA_VENTA BETWEEN ? AND ? GROUP BY METODO_PAGO";
        Map<String, Double> totales = new LinkedHashMap<>();

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, fechaInicio);
            ps.setString(2, fechaFin);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                totales.put(rs.getString("METODO_PAGO"), rs.getDouble("total"));
            }
        }

        return totales;
    }



    public List<String[]> obtenerTopProductos(String fechaInicio, String fechaFin, boolean mayores) throws SQLException {
        String orden = mayores ? "DESC" : "ASC";
        String sql = "SELECT d.ID_PRODUCTO, p.Nombre, SUM(d.CANTIDAD_PRODUCTO) as total " +
                "FROM detalle_venta d JOIN producto p ON d.ID_PRODUCTO = p.ID_PRODUCTO " +
                "JOIN venta v ON d.ID_VENTA = v.ID_VENTA " +
                "WHERE v.FECHA_VENTA BETWEEN ? AND ? " +
                "GROUP BY d.ID_PRODUCTO, p.Nombre " +
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
