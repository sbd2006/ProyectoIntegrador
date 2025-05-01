package Controlador;

import Modelo.ModeloIS;
import Vista.EmpleadoVista;
import Vista.VistaIS;
import Vista.VentaVista;


import javax.swing.*;

public class EmpleadoControlador extends JFrame {

    private final EmpleadoVista vista;
    private final String nombreUsuario; // Hacemos final el nombre del usuario

    public EmpleadoControlador(EmpleadoVista vista, String nombreUsuario) {
        this.vista = vista;
        this.nombreUsuario = nombreUsuario;
        initController();
    }

    private void initController() {
        vista.realizarVentaButton.addActionListener(e -> mostrarVentas());
        vista.cerrarSesionButton.addActionListener(e -> vista.cerrarSesion());
        vista.setNombreUsuario(nombreUsuario);
    }

    private void mostrarVentas() {
        VentaVista vistaVentas = new VentaVista();
        vistaVentas.mostrarVenta();
        vista.dispose();
    }

    public void iniciarVista() {
        vista.mostrarEmpleado();
    }
}

