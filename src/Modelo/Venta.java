package Modelo;

public class Venta {
    private int idVenta;
    private String fecha;
    private double total;
    private int idEmpleado;
    private int idCliente;
    private String metodoPago;


    public Venta(int idVenta, String fecha, double total, int idEmpleado, int idCliente) {
        this.idVenta = idVenta;
        this.fecha = fecha;
        this.total = total;
        this.idEmpleado = idEmpleado;
        this.idCliente = idCliente;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }


    public int getIdVenta() { return idVenta; }
    public String getFecha() { return fecha; }
    public double getTotal() { return total; }
    public int getIdEmpleado() { return idEmpleado; }

    public void setIdVenta(int idVenta) { this.idVenta = idVenta; }
    public void setFecha(String fecha) { this.fecha = fecha; }
    public void setTotal(double total) { this.total = total; }
    public void setIdEmpleado(int idEmpleado) { this.idEmpleado = idEmpleado; }
}
