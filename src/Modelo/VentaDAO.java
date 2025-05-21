package Modelo;

import javax.swing.*;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class VentaDAO {
    private final String URL = "jdbc:mysql://127.0.0.1:3306/PostresMariaJose";
    private final String USER = "root";
    private final String PASSWORD = "Juanguis-2006";

    public boolean registrarVentaCompleta(Venta venta, List<DetalleVenta> detalles) {
        Connection con = null;
        PreparedStatement psVenta = null;
        PreparedStatement psDetalle = null;
        PreparedStatement psStock = null;
        PreparedStatement psMovimiento = null;

        try {
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            con.setAutoCommit(false);


            String sqlVenta = "INSERT INTO venta (FECHA_VENTA, TOTAL, CANTIDAD, ID_CLIENTE, ID_EMPLEADO) VALUES (?, ?, ?, ?, ?)";
            psVenta = con.prepareStatement(sqlVenta, Statement.RETURN_GENERATED_KEYS);

            int cantidadTotal = detalles.stream().mapToInt(DetalleVenta::getCantidad).sum();

            psVenta.setString(1, venta.getFecha());
            psVenta.setDouble(2, venta.getTotal());
            psVenta.setInt(3, cantidadTotal);
            psVenta.setInt(4, venta.getIdCliente());
            psVenta.setInt(5, venta.getIdEmpleado());

            psVenta.executeUpdate();


            ResultSet rs = psVenta.getGeneratedKeys();
            int idVenta;
            if (rs.next()) {
                idVenta = rs.getInt(1);
                venta.setIdVenta(idVenta);
            } else {
                throw new SQLException("No se generó el ID de la venta.");
            }

            String sqlDetalle = "INSERT INTO detalle_venta (ID_PRODUCTO, CANTIDAD_PRODUCTO, DESCRIPCION, PRECIO_UNITARIO, ID_VENTA) VALUES (?, ?, ?, ?, ?)";
            psDetalle = con.prepareStatement(sqlDetalle);

            for (DetalleVenta d : detalles) {

                d.setIdVenta(idVenta);

                psDetalle.setString(1, d.getIdProducto());
                psDetalle.setInt(2, d.getCantidad());
                psDetalle.setString(3, d.getDescripcion());
                psDetalle.setDouble(4, d.getPrecioUnitario());
                psDetalle.setInt(5, idVenta);

                psDetalle.addBatch();

            }

            psDetalle.executeBatch();

            con.commit();
            return true;

        } catch (SQLException e) {
            try {
                if (con != null) con.rollback();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al hacer rollback: " + ex.getMessage());
            }
            JOptionPane.showMessageDialog(null, "Error al registrar venta: " + e.getMessage());
            return false;
        } finally {
            try {
                if (psVenta != null) psVenta.close();
                if (psDetalle != null) psDetalle.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public List<String[]> consultarPorFecha(String fecha) {
        List<String[]> resultados = new ArrayList<>();
        String sql = "{CALL consultaPorFecha(?)}";

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setString(1, fecha);
            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                String[] fila = new String[7];
                fila[0] = rs.getString("ID_VENTA");
                fila[1] = rs.getString("FECHA_VENTA");
                fila[2] = rs.getString("TOTAL");
                fila[3] = rs.getString("ID_PRODUCTO");
                fila[4] = rs.getString("CANTIDAD_PRODUCTO");
                fila[5] = rs.getString("PRECIO_UNITARIO");
                fila[6] = rs.getString("Nombre");
                resultados.add(fila);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al consultar ventas: " + e.getMessage());
        }

        return resultados;
    }

    public List<String[]> obtenerProductos() throws SQLException {
        List<String[]> productos = new ArrayList<>();
        String sql = "SELECT p.Id_producto, p.Nombre, c.Nombre, p.Precio, stock FROM producto p JOIN categoria c ON p.id_Categoria = c.id_Categoria ";

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                String[] registro = {
                        rs.getString("Id_producto"),
                        rs.getString("Nombre"),
                        rs.getString("c.Nombre"),
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

        return 0;

    }

}
