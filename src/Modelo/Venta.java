package Modelo;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Venta {
    private int idPedido;
    private LocalDate fecha;
    private List<Producto> productos = new ArrayList<>();

    public Venta(int idPedido, LocalDate fecha) {
        this.idPedido = idPedido;
        this.fecha = fecha;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }

    public double calcularTotal() {
        return productos.stream().mapToDouble(p -> p.getPrecio() * p.getCantidad()).sum();
    }
}