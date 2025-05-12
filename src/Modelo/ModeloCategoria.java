package Modelo;
import java.sql.*;

public class ModeloCategoria {
    private Connection conexion;

    public ModeloCategoria(Connection conexion) {
        this.conexion = conexion;
    }

    public int obtenerOCrearCategoria(String nombreCategoria) throws SQLException {
        String sqlBuscar = "SELECT ID_CATEGORIA FROM CATEGORIA WHERE NOMBRE = ?";
        PreparedStatement buscar = conexion.prepareStatement(sqlBuscar);
        buscar.setString(1, nombreCategoria);
        ResultSet rs = buscar.executeQuery();

        if (rs.next()) {
            return rs.getInt("ID_CATEGORIA");
        }

        String sqlInsertar = "INSERT INTO CATEGORIA (NOMBRE) VALUES (?)";
        PreparedStatement insertar = conexion.prepareStatement(sqlInsertar, Statement.RETURN_GENERATED_KEYS);
        insertar.setString(1, nombreCategoria);
        insertar.executeUpdate();

        ResultSet claves = insertar.getGeneratedKeys();
        if (claves.next()) {
            return claves.getInt(1);
        } else {
            throw new SQLException("No se pudo insertar la categoría.");
        }
    }

    public boolean insertarCategoria(String nombre) throws SQLException {
        String sqlBuscar = "SELECT 1 FROM categoria WHERE nombre = ?";
        PreparedStatement buscar = conexion.prepareStatement(sqlBuscar);
        buscar.setString(1, nombre);
        ResultSet rs = buscar.executeQuery();
        if (rs.next()) {
            return false;
        }else {
            String sql = "INSERT INTO categoria (Nombre) VALUES (?)";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.executeUpdate();
            return true;
        }
    }

    public boolean eliminarCategoria(String nombre) throws SQLException {
        String verificar = "SELECT COUNT(*) FROM producto WHERE ID_CATEGORIA = (SELECT ID_CATEGORIA FROM categoria WHERE Nombre = ?)";
        PreparedStatement psVerificar = conexion.prepareStatement(verificar);
        psVerificar.setString(1, nombre);
        ResultSet rs = psVerificar.executeQuery();

        if (rs.next() && rs.getInt(1) > 0) {
            return false;
        }

        String sql = "DELETE FROM categoria WHERE Nombre = ?";
        PreparedStatement ps = conexion.prepareStatement(sql);
        ps.setString(1, nombre);
        return ps.executeUpdate() > 0;
    }


}

