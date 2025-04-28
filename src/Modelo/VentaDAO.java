package Modelo;

import javax.swing.*;
import java.sql.*;
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
}