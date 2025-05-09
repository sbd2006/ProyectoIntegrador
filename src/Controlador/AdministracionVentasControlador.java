package Controlador;

import Modelo.VentaDAO;
import Vista.AdministracionVentasVista;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.List;

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
        List<String[]> datos = modelo.consultarFecha(fecha);

        for (String[] fila : datos) {
            vista.modeloTabla.addRow(fila);
        }
    }

    public void iniciarVista() {
        vista.mostrarVista();
    }
}
