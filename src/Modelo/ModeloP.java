package Modelo;

import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class ModeloP {
    private Connection conexion;
    private PreparedStatement ps;
    private ResultSet rs;
    private Statement st;

    public ModeloP() throws SQLException {
        Conexion conX = new Conexion();
        conX.conectar();
        conexion = conX.getConexion();
    }

    public DefaultTableModel mostrarProductos() throws SQLException {
        String[] columnas = {"Id", "Nombre", "Categoria", "Precio", "Cantidad_Actual"};
        DefaultTableModel modeloTabla = new DefaultTableModel(null, columnas);
        String[] registros = new String[5];

        st = conexion.createStatement();
        rs = st.executeQuery("SELECT ID_PRODUCTO, Nombre, Categoria, Precio, Cantidad_Actual FROM Producto");

        while (rs.next()) {
            registros[0] = rs.getString("ID_PRODUCTO");
            registros[1] = rs.getString("Nombre");
            registros[2] = rs.getString("Categoria");
            registros[3] = rs.getString("Precio");
            registros[4] = rs.getString("Cantidad_Actual");
            modeloTabla.addRow(registros);
        }
        return modeloTabla;
    }

    public boolean guardarProducto(int id, String nombre, String categoria, int precio, int cantidad) throws SQLException {
        ps = conexion.prepareStatement("INSERT INTO Producto (ID_PRODUCTO, Nombre, Categoria, Precio, Cantidad_Actual) VALUES (?, ?, ?, ?, ?)");
        ps.setInt(1, id);
        ps.setString(2, nombre);
        ps.setString(3, categoria);
        ps.setInt(4, precio);
        ps.setInt(5, cantidad);
        return ps.executeUpdate() > 0;
    }

    public boolean editarProducto(int id, int precio, int cantidad) throws SQLException {
        ps = conexion.prepareStatement("UPDATE Producto SET Precio = ?, Cantidad_Actual = ? WHERE ID_PRODUCTO = ?");
        ps.setInt(1, precio);
        ps.setInt(2, cantidad);
        ps.setInt(3, id);
        return ps.executeUpdate() > 0;
    }

    public boolean eliminarProducto(int id) throws SQLException {
        ps = conexion.prepareStatement("DELETE FROM Producto WHERE ID_PRODUCTO = ?");
        ps.setInt(1, id);
        return ps.executeUpdate() > 0;
    }
}
