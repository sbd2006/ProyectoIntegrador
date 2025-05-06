package Modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RolDAO {
    private Connection conexion;

    public RolDAO() throws SQLException {

        conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/PostresMariaJose", "root", "Juanguis-2006");

    }

    public List<String[]> consultarUsuarios() throws SQLException {
        List<String[]> lista = new ArrayList<>();
        String[] registro;
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery("SELECT id, Nombre, Apellido, tipo, Usuario FROM usuarios");

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
        PreparedStatement ps = conexion.prepareStatement("UPDATE Usuarios SET tipo = ? WHERE id = ?");
        ps.setString(1, tipo);
        ps.setInt(2, id);
        return ps.executeUpdate() > 0;
    }

    public boolean eliminarUsuario(int id) throws SQLException {
        PreparedStatement ps = conexion.prepareStatement("DELETE FROM Usuarios WHERE id = ?");
        ps.setInt(1, id);
        return ps.executeUpdate() > 0;
    }
}
