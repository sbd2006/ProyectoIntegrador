package Modelo;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VentaDAO extends JFrame {
    private final Connection connection;

    public VentaDAO(Connection connection) {
        this.connection = connection;
    }

    public void guardarVenta(Venta venta) throws SQLException {
        String sqlPedido = "INSERT INTO pedidos (id_pedido, fecha) VALUES (?, ?)";
        String sqlDetalle = "INSERT INTO detalle_pedido (id_pedido, id_producto, cantidad, precio) VALUES (?, ?, ?, ?)";

        try (PreparedStatement psPedido = connection.prepareStatement(sqlPedido);
             PreparedStatement psDetalle = connection.prepareStatement(sqlDetalle)) {

            psPedido.setInt(1, venta.getIdPedido());
            psPedido.setDate(2, Date.valueOf(venta.getFecha()));
            psPedido.executeUpdate();

            for (Producto p : venta.getProductos()) {
                psDetalle.setInt(1, venta.getIdPedido());
                psDetalle.setInt(2, p.getId());
                psDetalle.setInt(3, p.getCantidad());
                psDetalle.setDouble(4, p.getPrecio());
                psDetalle.executeUpdate();
            }
        }
    }

    public List<String[]> consultarPorFecha(String fecha) {
        List<String[]> resultados = new ArrayList<>();

        String sql = "SELECT * FROM ventas WHERE fecha = ?"; // Ajusta la tabla y columna si es necesario

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, fecha);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String idVenta = rs.getString("id_venta");
                String cliente = rs.getString("cliente");
                String total = rs.getString("total");
                String fechaVenta = rs.getString("fecha");

                String[] fila = {idVenta, cliente, total, fechaVenta};
                resultados.add(fila);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultados;
    }
}