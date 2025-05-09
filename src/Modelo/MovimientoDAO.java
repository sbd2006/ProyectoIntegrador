package Modelo;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovimientoDAO {

    Conexion con1 = new Conexion();
        public boolean registrarMovimiento(ModeloMovimiento movimiento, String nroDocumento) {
            String insertDocumentoSQL = "INSERT INTO documento (FECHA_DOCUMENTO, NRO_DOCUMENTO, ID_TIPO_DOCUMENTO) VALUES (?, ?, ?)";
            String movimientoSql = "INSERT INTO movimiento (CANTIDAD, FECHA_MOVIMIENTO, OBSERVACION, ID_PRODUCTO, ID_DOCUMENTO) VALUES (?, ?, ?, ?, ?)";
            String stockSql = "UPDATE producto SET stock = stock + ? WHERE ID_PRODUCTO = ?";

            con1.conectar();
            try (Connection conn = con1.getConexion()) {
                conn.setAutoCommit(false);

                // 1. Obtener ID_TIPO_DOCUMENTO y SIGNO
                String tipoDocQuery = "SELECT ID_TIPO_DOCUMENTO, SIGNO FROM tipo_documento WHERE DESCRIPCION = ?";
                int tipoDocId;
                int signo;
                try (PreparedStatement psTipo = conn.prepareStatement(tipoDocQuery)) {
                    psTipo.setString(1, movimiento.getTipoMovimiento());
                    var rs = psTipo.executeQuery();
                    if (rs.next()) {
                        tipoDocId = rs.getInt("ID_TIPO_DOCUMENTO");
                        signo = rs.getInt("SIGNO");
                    } else {
                        throw new SQLException("Tipo de documento no encontrado.");
                    }
                }

                // 2. Insertar en DOCUMENTO y obtener ID_DOCUMENTO generado
                int idDocumentoGenerado;
                try (PreparedStatement psDoc = conn.prepareStatement(insertDocumentoSQL, Statement.RETURN_GENERATED_KEYS)) {
                    psDoc.setString(1, movimiento.getFechaMovimiento());
                    psDoc.setString(2, nroDocumento);
                    psDoc.setInt(3, tipoDocId);

                    psDoc.executeUpdate();

                    ResultSet generatedKeys = psDoc.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        idDocumentoGenerado = generatedKeys.getInt(1);
                    } else {
                        throw new SQLException("No se pudo obtener el ID del documento insertado.");
                    }
                }

                // 3. Insertar en MOVIMIENTO
                try (PreparedStatement pstmt = conn.prepareStatement(movimientoSql)) {
                    pstmt.setInt(1, movimiento.getCantidad());
                    pstmt.setString(2, movimiento.getFechaMovimiento());
                    pstmt.setString(3, movimiento.getObservacion());
                    pstmt.setInt(4, movimiento.getProductoId());
                    pstmt.setInt(5, idDocumentoGenerado); // foreign key
                    pstmt.executeUpdate();
                }

                // 4. Actualizar STOCK
                try (PreparedStatement psStock = conn.prepareStatement(stockSql)) {
                    psStock.setInt(1, movimiento.getCantidad() * signo); // aplicar el signo
                    psStock.setInt(2, movimiento.getProductoId());
                    psStock.executeUpdate();
                }

                conn.commit();
                return true;

            } catch (SQLException ex) {
                ex.printStackTrace();
                return false;
            }
        }

    public List<String> obtenerTiposDocumento() {
        List<String> tipos = new ArrayList<>();
        String sql = "SELECT DESCRIPCION FROM tipo_documento";

        con1.conectar();
        try (Connection conn = con1.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                tipos.add(rs.getString("DESCRIPCION"));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return tipos;
    }

    public List<String[]> obtenerMovimientos() {
        List<String[]> lista = new ArrayList<>();
        String sql = "SELECT p.NOMBRE, m.CANTIDAD, m.FECHA_MOVIMIENTO, m.OBSERVACION " +
                "FROM movimiento m " +
                "JOIN producto p ON m.ID_PRODUCTO = p.ID_PRODUCTO";

        con1.conectar();
        try (Connection conn = con1.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String[] fila = new String[4];
                fila[0] = String.valueOf(rs.getString("NOMBRE"));
                fila[1] = String.valueOf(rs.getInt("CANTIDAD"));
                fila[2] = rs.getString("FECHA_MOVIMIENTO");
                fila[3] = rs.getString("OBSERVACION");
                lista.add(fila);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public List<String[]> obtenerDocumentos() {
        List<String[]> lista = new ArrayList<>();
        String sql = "SELECT ID_DOCUMENTO, FECHA_DOCUMENTO, NRO_DOCUMENTO FROM documento";

        con1.conectar();
        try (Connection conn = con1.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String[] fila = new String[3];
                fila[0] = String.valueOf(rs.getInt("ID_DOCUMENTO"));
                fila[1] = rs.getString("FECHA_DOCUMENTO");
                fila[2] = rs.getString("NRO_DOCUMENTO");
                lista.add(fila);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

}