package Modelo;

import java.sql.*;
import java.util.*;

public class ModeloP {
    private Connection conexion;

    public ModeloP() {
        Conexion con = new Conexion();
        con.conectar();
        this.conexion = con.getConexion();
    }

    public Connection getConexion() {
        return conexion;
    }
    public void guardarProducto(String id, String nombre, int idCategoria, String precio) throws SQLException {
        String sql = "INSERT INTO producto (id_producto, nombre, id_categoria, precio) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = conexion.prepareStatement(sql);
        ps.setString(1, id);
        ps.setString(2, nombre);
        ps.setInt(3, idCategoria);
        ps.setString(4, precio);
        ps.executeUpdate();
    }

    public void editarProducto(String id, String precio) throws SQLException {
        String sql = "UPDATE producto SET precio = ? WHERE id_producto = ?";
        PreparedStatement ps = conexion.prepareStatement(sql);
        ps.setString(1, precio);
        ps.setString(2, id);
        ps.executeUpdate();
    }

    public void eliminarProducto(String id) throws SQLException {
        String sql = "DELETE FROM producto WHERE id_producto = ?";
        PreparedStatement ps = conexion.prepareStatement(sql);
        ps.setString(1, id);
        ps.executeUpdate();
    }

    public List<String[]> obtenerProductos() throws SQLException {
        List<String[]> productos = new ArrayList<>();
        String sql = "SELECT p.id_producto, p.nombre, c.nombre AS categoria, p.precio, p.stock " +
                "FROM producto p JOIN categoria c ON p.id_categoria = c.id_categoria";
        Statement stmt = conexion.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            String[] fila = new String[5];
            fila[0] = rs.getString("id_producto");
            fila[1] = rs.getString("nombre");
            fila[2] = rs.getString("categoria");
            fila[3] = rs.getString("precio");
            fila[4] = rs.getString("stock");
            productos.add(fila);
        }
        return productos;
    }

    public Map<String, Integer> obtenerProductosNombreId() {
        Map<String, Integer> mapa = new HashMap<>();
        String sql = "SELECT id_producto, nombre FROM producto";
        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                mapa.put(rs.getString("nombre"), rs.getInt("id_producto"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mapa;
    }
}