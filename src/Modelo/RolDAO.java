package Modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RolDAO {
    private Connection conexion;

    public RolDAO() throws SQLException {

        conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/PostresMariaJose", "root", "OH{c<6H1#cQ%F69$i");

    }

    public List<String[]> consultarUsuarios() throws SQLException {
        List<String[]> lista = new ArrayList<>();
        String[] registro;
        CallableStatement cs = conexion.prepareCall("{CALL mostrarUsuarios()}");
        ResultSet rs = cs.executeQuery();


        while (rs.next()) {
            registro = new String[5];
            registro[0] = rs.getString("id");
            registro[1] = rs.getString("nombre");
            registro[2] = rs.getString("apellido");
            registro[3] = rs.getString("tipo");
            registro[4] = rs.getString("usuario");
            lista.add(registro);
        }
        return lista;
    }

    public boolean actualizarRol(int id, String tipo) throws SQLException {
        CallableStatement cs = conexion.prepareCall("{CALL actualizarRol(?,?)}");
        cs.setInt(1, id);
        cs.setString(2, tipo);
        return cs.executeUpdate() > 0;
    }

    public boolean eliminarUsuario(int id) throws SQLException {
        CallableStatement cs = conexion.prepareCall("{CALL eliminarRol(?)}");
        cs.setInt(1, id);
        return cs.executeUpdate() > 0;
    }
}
