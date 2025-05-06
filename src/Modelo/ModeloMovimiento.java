package Modelo;

public class ModeloMovimiento {
    private int id;
    private int productoId;
    private String tipoMovimiento;
    private int documentoId;
    private int estadoMovimientoId;
    private int cantidad;
    private String fechaMovimiento;
    private String observacion;

    public ModeloMovimiento(int productoId, String tipoMovimiento, int documentoId, int estadoMovimientoId, int cantidad, String fechaMovimiento, String observacion) {
        this.productoId = productoId;
        this.tipoMovimiento = tipoMovimiento;
        this.documentoId = documentoId;
        this.estadoMovimientoId = estadoMovimientoId;
        this.cantidad = cantidad;
        this.fechaMovimiento = fechaMovimiento;
        this.observacion = observacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductoId() {
        return productoId;
    }

    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public int getDocumentoId() {
        return documentoId;
    }

    public void setDocumentoId(int documentoId) {
        this.documentoId = documentoId;
    }

    public int getEstadoMovimientoId() {
        return estadoMovimientoId;
    }

    public void setEstadoMovimientoId(int estadoMovimientoId) {
        this.estadoMovimientoId = estadoMovimientoId;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getFechaMovimiento() {
        return fechaMovimiento;
    }

    public void setFechaMovimiento(String fechaMovimiento) {
        this.fechaMovimiento = fechaMovimiento;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}

