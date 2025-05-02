package Modelo;

public class DetalleVenta {
    private int idVenta;
    private String idProducto;
    private int cantidad;
    private String descripcion;
    private double precioUnitario;
    private double totalProducto;

    public DetalleVenta(int idVenta, String idProducto, int cantidad, String descripcion, double precioUnitario, double totalProducto) {
        this.idVenta = idVenta;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.descripcion = descripcion;
        this.precioUnitario = precioUnitario;
        this.totalProducto = totalProducto;
    }

    public int getIdVenta() { return idVenta; }
    public String getIdProducto() { return idProducto; }
    public int getCantidad() { return cantidad; }
    public String getDescripcion() { return descripcion; }
    public double getPrecioUnitario() { return precioUnitario; }
    public double getTotalProducto() { return totalProducto; }

    public void setIdVenta(int idVenta) { this.idVenta = idVenta; }
    public void setIdProducto(String idProducto) { this.idProducto = idProducto; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setPrecioUnitario(double precioUnitario) { this.precioUnitario = precioUnitario; }
    public void setTotalProducto(double totalProducto) { this.totalProducto = totalProducto; }
}