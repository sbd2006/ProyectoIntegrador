package Modelo;

public class Venta {
    private int idVenta;
    private String fecha;
    private double total;

    public Venta(int idVenta, String fecha, double total) {
        this.idVenta = idVenta;
        this.fecha = fecha;
        this.total = total;
    }

    public int getIdVenta() { return idVenta; }
    public String getFecha() { return fecha; }
    public double getTotal() { return total; }

    public void setIdVenta(int idVenta) { this.idVenta = idVenta; }
    public void setFecha(String fecha) { this.fecha = fecha; }
    public void setTotal(double total) { this.total = total; }
}