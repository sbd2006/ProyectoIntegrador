package Modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.table.DefaultTableModel;


public class ModeloP {
    private Conexion conSQL;

    public ModeloP() {
        conSQL = new Conexion();
        conSQL.conectar();
    }
    public Connection getConexion() {
        return conSQL.getConexion();
    }

    public List<String[]> obtenerProductos() throws SQLException {
        List<String[]> productos = new ArrayList<>();
        Statement st = getConexion().createStatement();
        ResultSet rs = st.executeQuery("SELECT Id_producto, Nombre, Categoria, Precio, stock FROM Producto");

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
        return productos;
    }

    public boolean guardarProducto(String id, String nombre, String categoria, String precio, String cantidad) throws SQLException {
        PreparedStatement ps = getConexion().prepareStatement("INSERT INTO Producto (Id_producto, Nombre, Categoria, Precio, stock) VALUES (?, ?, ?, ?, ?)");
        ps.setInt(1, Integer.parseInt(id));
        ps.setString(2, nombre);
        ps.setString(3, categoria);
        ps.setInt(4, Integer.parseInt(precio));
        ps.setInt(5, Integer.parseInt(cantidad));
        return ps.executeUpdate() > 0;
    }

    public boolean editarProducto(String id, String precio, String cantidad) throws SQLException {
        PreparedStatement ps = getConexion().prepareStatement("UPDATE Producto SET Nombre= ?, Categoria= ?, Precio = ? WHERE Id_producto = ?");
        ps.setInt(1, Integer.parseInt(precio));
        ps.setInt(2, Integer.parseInt(cantidad));
        ps.setInt(3, Integer.parseInt(id));
        return ps.executeUpdate() > 0;
    }

    public boolean eliminarProducto(String id) throws SQLException {
        PreparedStatement ps = getConexion().prepareStatement("DELETE FROM Producto WHERE Id_producto = ?");
        ps.setInt(1, Integer.parseInt(id));
        return ps.executeUpdate() > 0;
    }

    public DefaultTableModel mostrarProductos() {
        DefaultTableModel modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre", "Categoria", "Precio", "Cantidad"}, 0);
        try {
            List<String[]> productos = obtenerProductos();
            for (String[] producto : productos) {
                modeloTabla.addRow(producto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return modeloTabla;
    }
    public Map<String, Integer> obtenerProductosNombreId() {
        Map<String, Integer> productos = new HashMap<>();
        try {
            Connection conn = getConexion();
            String sql = "SELECT id_producto, nombre FROM producto";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                productos.put(rs.getString("nombre"), rs.getInt("id_producto"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productos;
    }
}


