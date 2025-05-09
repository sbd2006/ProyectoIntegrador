package Modelo;

import javax.swing.*;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class VentaDAO {
    private final String URL = "jdbc:mysql://127.0.0.1:3306/PostresMariaJose";
    private final String USER = "root";

    private final String PASSWORD = "OH{c<6H1#cQ%F69$i";



    public int insertarVentaCliente(Venta venta, List<DetalleVenta> detalles, int clienteId) throws SQLException {
        String sql = "INSERT INTO venta (FECHA_VENTA, TOTAL, CANTIDAD, ID_CLIENTE, ID_EMPLEADO) VALUES (?, ?, ?, ?, ?)";


        int cantidadTotal = detalles.stream().mapToInt(DetalleVenta::getCantidad).sum();

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, venta.getFecha());
            ps.setDouble(2, venta.getTotal());
            ps.setInt(3, cantidadTotal);
            ps.setInt(4, clienteId);
            ps.setInt(5, venta.getIdEmpleado()); // Usa el nuevo campo

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }

        throw new SQLException("No se generó ID de la venta con cliente.");
    }





    public void insertarDetalles(List<DetalleVenta> detalles) throws SQLException {
        String sql = "INSERT INTO detalle_venta (ID_PRODUCTO, CANTIDAD_PRODUCTO, DESCRIPCION, PRECIO_UNITARIO, ID_VENTA) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = con.prepareStatement(sql)) {

            for (DetalleVenta d : detalles) {
                ps.setString(1, d.getIdProducto());
                ps.setInt(2, d.getCantidad());
                ps.setString(3, d.getDescripcion());
                ps.setDouble(4, d.getPrecioUnitario());

                ps.setInt(5, d.getIdVenta());

                ps.addBatch();
            }

                ps.setDouble(5, d.getTotalProducto());
                ps.setInt(6, d.getIdVenta());
                ps.addBatch();
            }

            ps.executeBatch();
        }


        for (DetalleVenta d : detalles) {
            actualizarStock(d.getIdProducto(), d.getCantidad());
        }
    }


    public List<String[]> consultarFecha(String fecha) {

        List<String[]> resultados = new ArrayList<>();
        String sql = "SELECT v.ID_VENTA, v.FECHA_VENTA, v.TOTAL, d.ID_PRODUCTO, d.CANTIDAD_PRODUCTO, d.PRECIO_UNITARIO, d.TOTAL_PRODUCTO, p.Nombre FROM Venta v JOIN detalle_venta d ON v.ID_VENTA = d.ID_VENTA JOIN producto p ON d.ID_PRODUCTO = p.Id_producto WHERE v.FECHA_VENTA = ?";

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, fecha);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String[] fila = new String[8];
                fila[0] = rs.getString("ID_VENTA");
                fila[1] = rs.getString("FECHA_VENTA");
                fila[2] = rs.getString("TOTAL");
                fila[3] = rs.getString("ID_PRODUCTO");
                fila[4] = rs.getString("CANTIDAD_PRODUCTO");
                fila[5] = rs.getString("PRECIO_UNITARIO");
                fila[6] = rs.getString("TOTAL_PRODUCTO");
                fila[7] = rs.getString("Nombre");
                resultados.add(fila);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al consultar ventas: " + e.getMessage());
        }

        return resultados;
    }

    public List<String[]> obtenerProductos() throws SQLException {
        List<String[]> productos = new ArrayList<>();

        String sql = "SELECT Id_producto, Nombre, Categoria, Precio, stock FROM producto";

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                String[] registro = {
                        rs.getString("Id_producto"),
                        rs.getString("Nombre"),
                        rs.getString("Categoria"),
                        rs.getString("Precio"),
                        rs.getString("stock")
                };
                productos.add(registro);
            }
        }

        return productos;
    }

    public String obtenerNombreProducto(String idProducto) throws SQLException {
        String sql = "SELECT Nombre FROM producto WHERE Id_producto = ?";
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, idProducto);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("Nombre");
            }
        }
        return "";
    }

    public void actualizarStock(String idProducto, int cantidadVendida) throws SQLException {
        String sql = "UPDATE producto SET stock = stock - ? WHERE Id_producto = ?";
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, cantidadVendida);
            ps.setString(2, idProducto);
            ps.executeUpdate();
        }
    }
    public int obtenerStockActual(String idProducto) throws SQLException {
        String sql = "SELECT stock FROM producto WHERE Id_producto = ?";
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, idProducto);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("stock");
            }
        }
        return -1; // o lanzar una excepción si el producto no existe
    }
}
