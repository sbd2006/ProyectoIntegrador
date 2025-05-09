package Modelo;

import java.sql.*;

public class ClienteDAO {
    private final Conexion conexion;

    public ClienteDAO() {
        this.conexion = new Conexion();
    }

    public int obtenerIdOCrear(String nombreCliente, String telefono, String direccion) throws SQLException {
        int id = buscarClientePorNombre(nombreCliente);
        if (id > 0) return id;
        return insertarNuevoCliente(nombreCliente, telefono, direccion);
    }

    private int buscarClientePorNombre(String nombreCliente) throws SQLException {
        String sql = "SELECT ID_CLIENTE FROM cliente WHERE NOMBRE = ?";
        conexion.conectar();
        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombreCliente);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("ID_CLIENTE");
            }
        }
        return -1;
    }

    private int insertarNuevoCliente(String nombreCliente, String telefono, String direccion) throws SQLException {
        String sql = "INSERT INTO cliente (NOMBRE, Telefono, Direccion) VALUES (?, ?, ?)";
        conexion.conectar();
        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, nombreCliente);
            ps.setString(2, telefono == null || telefono.isEmpty() ? null : telefono);
            ps.setString(3, direccion == null || direccion.isEmpty() ? null : direccion);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                throw new SQLException("No se pudo generar ID del cliente");
            }
        }
    }
}