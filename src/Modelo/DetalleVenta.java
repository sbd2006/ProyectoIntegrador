package Modelo;

import java.util.List;

public class DetalleVenta {
    private int idPedido;
    private String fecha;
    private double total;
    private List<ProductoVenta> productos;

    public DetalleVenta(int idPedido, String fecha, double total, List<ProductoVenta> productos) {
        this.idPedido = idPedido;
        this.fecha = fecha;
        this.total = total;
        this.productos = productos;
    }

    public int getIdPedido() { return idPedido; }
    public void setIdPedido(int idPedido) { this.idPedido = idPedido; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public List<ProductoVenta> getProductos() { return productos; }
    public void setProductos(List<ProductoVenta> productos) { this.productos = productos; }
}
