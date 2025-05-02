package Controlador;


import Modelo.VentaDAO;

import Vista.EmpleadoVista;
import Vista.VistaIS;
import Vista.VentaVista;


import javax.swing.*;

public class EmpleadoControlador extends JFrame {

    private final EmpleadoVista vista;
    private final String nombreUsuario; 

    public EmpleadoControlador(EmpleadoVista vista, String nombreUsuario) {
        this.vista = vista;
        this.nombreUsuario = nombreUsuario;
        initController();
    }

    private void initController() {

        vista.realizarVentaButton.addActionListener(e -> mostrarVenta());
        vista.cerrarSesionButton.addActionListener(e -> cerrarSesion());

        vista.setNombreUsuario(nombreUsuario);
    }

    private void mostrarVenta() {
        VentaVista vista = new VentaVista();
        VentaDAO dao = new VentaDAO();
        VentaControlador controlador = new VentaControlador(vista, dao);
        vista.setVisible(true);  
    }



    private void cerrarSesion() {
        VistaIS inicio = new VistaIS();
        inicio.mostrarVista();
        vista.dispose();
    }


    public void iniciarVista() {
        vista.mostrarEmpleado();
    }
}

