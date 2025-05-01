package Modelo;

public class ModeloMovimiento {
    private int id;
    private int productoId;
    private int tipoMovimientoId;
    private int documentoId;
    private int estadoMovimientoId;
    private int cantidad;
    private String fechaMovimiento;
    private String observacion;

    public ModeloMovimiento(int productoId, int tipoMovimientoId, int documentoId, int estadoMovimientoId, int cantidad, String fechaMovimiento, String observacion) {
        this.productoId = productoId;
        this.tipoMovimientoId = tipoMovimientoId;
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

    public int getTipoMovimientoId() {
        return tipoMovimientoId;
    }

    public void setTipoMovimientoId(int tipoMovimientoId) {
        this.tipoMovimientoId = tipoMovimientoId;
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

