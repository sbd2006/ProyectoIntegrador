package Controlador;

import Modelo.VentaDAO;
import Vista.AdministracionVentasVista;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AdministracionVentasControlador {

    private final AdministracionVentasVista vista;
    private final VentaDAO modelo;

    public AdministracionVentasControlador(AdministracionVentasVista vista, VentaDAO modelo) {
        this.vista = vista;
        this.modelo = modelo;
        initController();
    }

    private void initController() {
        vista.consultarButton.addActionListener(e -> consultar());
    }

    private void consultar() {
        vista.modeloTabla.setRowCount(0);

        if (vista.selectorFecha.getDate() == null) {
            JOptionPane.showMessageDialog(vista, "Por favor selecciona una fecha.");
            return;
        }

        String fecha = new SimpleDateFormat("yyyy-MM-dd").format(vista.selectorFecha.getDate());
        List<String[]> datos = modelo.consultarPorFecha(fecha);


        Map<String, StringBuilder> productosPorVenta = new LinkedHashMap<>();
        Map<String, String> fechas = new HashMap<>();
        Map<String, String> totales = new HashMap<>();
        Map<String, String> metodosPago = new HashMap<>();

        for (String[] fila : datos) {
            String idVenta = fila[0];
            String fechaVenta = fila[1];
            String total = fila[2];
            String producto = fila[6];
            String cantidad = fila[4];
            String metodoPago = fila[7];

            fechas.put(idVenta, fechaVenta);
            totales.put(idVenta, total);
            metodosPago.put(idVenta, metodoPago);

            productosPorVenta.putIfAbsent(idVenta, new StringBuilder());
            productosPorVenta.get(idVenta)
                    .append(cantidad).append(" x ").append(producto).append(", ");
        }


        for (String idVenta : productosPorVenta.keySet()) {
            String fechaVenta = fechas.get(idVenta);
            String total = totales.get(idVenta);
            String productosFormateados = productosPorVenta.get(idVenta).toString();
            String metodoPago = metodosPago.get(idVenta);


            if (productosFormateados.endsWith(", ")) {
                productosFormateados = productosFormateados.substring(0, productosFormateados.length() - 2);
            }

            vista.modeloTabla.addRow(new String[]{idVenta, fechaVenta, productosFormateados, total, metodoPago});
        }
    }
    public void iniciarVista() {
        vista.mostrarVista();
    }
}
