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
            con.setAutoCommit(false); // Inicia transacción

            // 1. Insertar venta
            String sqlVenta = "INSERT INTO venta (FECHA_VENTA, TOTAL, CANTIDAD, precio, DESCUENTO) VALUES (?, ?, ?, ?, ?)";
            psVenta = con.prepareStatement(sqlVenta, Statement.RETURN_GENERATED_KEYS);

            int cantidadTotal = detalles.stream().mapToInt(DetalleVenta::getCantidad).sum();
            double precioTotal = detalles.stream().mapToDouble(DetalleVenta::getPrecioUnitario).sum();

            psVenta.setString(1, venta.getFecha());
            psVenta.setDouble(2, venta.getTotal());
            psVenta.setInt(3, cantidadTotal);
            psVenta.setDouble(4, precioTotal);
            psVenta.setDouble(5, 0); // Descuento fijo por ahora

            psVenta.executeUpdate();

            ResultSet rs = psVenta.getGeneratedKeys();
            int idVenta;
            if (rs.next()) {
                idVenta = rs.getInt(1);
                venta.setIdVenta(idVenta);
            } else {
                throw new SQLException("No se generó el ID de la venta.");
            }

            // 2. Insertar detalles de la venta
            String sqlDetalle = "INSERT INTO detalle_venta (ID_PRODUCTO, CANTIDAD_PRODUCTO, DESCRIPCION, PRECIO_UNITARIO, TOTAL_PRODUCTO, ID_VENTA) VALUES (?, ?, ?, ?, ?, ?)";
            psDetalle = con.prepareStatement(sqlDetalle);

            // 3. Preparar actualización de stock
            String sqlStock = "UPDATE producto SET stock = stock - ? WHERE Id_producto = ?";
            psStock = con.prepareStatement(sqlStock);

            // 4. Preparar movimiento de inventario
            String sqlMovimiento = "INSERT INTO movimiento (ID_PRODUCTO, CANTIDAD, FECHA_MOVIMIENTO, OBSERVACION ) VALUES (?, ?, ?, ?)";
            psMovimiento = con.prepareStatement(sqlMovimiento);
            String fechaHoy = java.time.LocalDate.now().toString();

            for (DetalleVenta d : detalles) {
                d.setIdVenta(idVenta);

                // Insertar detalle
                psDetalle.setString(1, d.getIdProducto());
                psDetalle.setInt(2, d.getCantidad());
                psDetalle.setString(3, d.getDescripcion());
                psDetalle.setDouble(4, d.getPrecioUnitario());
                psDetalle.setDouble(5, d.getTotalProducto());
                psDetalle.setInt(6, idVenta);
                psDetalle.addBatch();

                // Actualizar stock
                psStock.setInt(1, d.getCantidad());
                psStock.setString(2, d.getIdProducto());
                psStock.addBatch();

                // Insertar movimiento
                psMovimiento.setString(1, d.getIdProducto());
                psMovimiento.setInt(2, d.getCantidad());
                psMovimiento.setString(3, fechaHoy);
                psMovimiento.setString(4, "Venta - " + idVenta);
                psMovimiento.addBatch();
            }

            psDetalle.executeBatch();
            psStock.executeBatch();
            psMovimiento.executeBatch();

            con.commit(); // ✅ Confirmar transacción
            return true;

        } catch (SQLException e) {
            try {
                if (con != null) con.rollback(); // ❌ Revertir si falla
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al hacer rollback: " + ex.getMessage());
            }
            JOptionPane.showMessageDialog(null, "Error al registrar venta: " + e.getMessage());
            return false;
        } finally {
            try {
                if (psVenta != null) psVenta.close();
                if (psDetalle != null) psDetalle.close();
                if (psStock != null) psStock.close();
                if (psMovimiento != null) psMovimiento.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    // ✅ Este método aún puede servir para consultas por fecha
    public List<String[]> consultarPorFecha(String fecha) {
        List<String[]> resultados = new ArrayList<>();
        String sql = "SELECT v.ID_VENTA, v.FECHA_VENTA, v.TOTAL, d.ID_PRODUCTO, d.CANTIDAD_PRODUCTO, d.PRECIO_UNITARIO, d.TOTAL_PRODUCTO, p.Nombre " +
                "FROM Venta v JOIN detalle_venta d ON v.ID_VENTA = d.ID_VENTA JOIN producto p ON d.ID_PRODUCTO = p.Id_producto " +
                "WHERE v.FECHA_VENTA = ?";

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

    // ✅ También se puede dejar este si lo usas para cargar productos al combobox o tabla
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
        return 0; // Retornar 0 si no se encuentra
    }

}
