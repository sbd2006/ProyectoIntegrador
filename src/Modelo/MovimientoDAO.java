package Modelo;

import Modelo.ModeloMovimiento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MovimientoDAO {

    Conexion con1 = new Conexion();
    public boolean registrarMovimiento(ModeloMovimiento movimiento) {
        String sql = "INSERT INTO movimiento (producto_id, tipo_movimiento_id, documento_id, estado_movimiento_id, cantidad, fecha_movimiento, observacion) VALUES (?, ?, ?, ?, ?, ?, ?)";
        con1.conectar();
        try (Connection conn = con1.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, movimiento.getProductoId());
            pstmt.setInt(2, movimiento.getTipoMovimientoId());
            pstmt.setInt(3, movimiento.getDocumentoId());
            pstmt.setInt(4, movimiento.getEstadoMovimientoId());
            pstmt.setInt(5, movimiento.getCantidad());
            pstmt.setString(6, movimiento.getFechaMovimiento());
            pstmt.setString(7, movimiento.getObservacion());

            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
