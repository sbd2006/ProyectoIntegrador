package Modelo;

public class Producto {
    private int id;
    private String descripcion;
    private double precio;
    private int cantidad;

    public Producto(int id, String descripcion, double precio, int cantidad) {
        this.id = id;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public int getId() { return id; }
    public String getDescripcion() { return descripcion; }
    public double getPrecio() { return precio; }
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
}
