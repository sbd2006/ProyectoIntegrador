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
    public void guardarProducto(String nombre, int idCategoria, String precio) throws SQLException {
        String sql = "{CALL guardarProducto(?,?,?)}";
        CallableStatement cs = conexion.prepareCall(sql);
        cs.setString(1, nombre);
        cs.setInt(2, idCategoria);
        cs.setString(3, precio);
        cs.executeUpdate();
    }

    public void editarProducto(String id, String precio) throws SQLException {

        String sql = "{CALL editarProducto(?,?)}";
        CallableStatement cs = conexion.prepareCall(sql);
        cs.setString(1, precio);
        cs.setString(2, id);
        cs.executeUpdate();
    }

    public void eliminarProducto(String id) throws SQLException {

        String sql = "{CALL eliminarProducto(?)}";
        CallableStatement cs = conexion.prepareCall(sql);
        cs.setString(1, id);
        cs.executeUpdate();
    }

    public List<String[]> obtenerProductos() throws SQLException {
        List<String[]> productos = new ArrayList<>();
        String sql = "{CALL obtenerProductos()}";
        CallableStatement cs = conexion.prepareCall(sql);
        ResultSet rs = cs.executeQuery(sql);
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