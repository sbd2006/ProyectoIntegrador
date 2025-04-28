package Modelo;

public class ProductoVenta {
    private int id;
    private String descripcion;
    private double precioUnitario;

    public ProductoVenta(int id, String descripcion, double precioUnitario) {
        this.id = id;
        this.descripcion = descripcion;
        this.precioUnitario = precioUnitario;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(double precioUnitario) { this.precioUnitario = precioUnitario; }
}

