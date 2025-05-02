package Controlador;


import Modelo.ModeloIS;
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

        vista.realizarVentaButton.addActionListener(e -> vista.mostrarVenta());
        vista.cerrarSesionButton.addActionListener(e -> cerrarSesion());

        vista.setNombreUsuario(nombreUsuario);
    }

    private void cerrarSesion() {
        vista.dispose();

        VistaIS nuevaVistaLogin = new VistaIS();
        ModeloIS nuevoModeloLogin = new ModeloIS(nuevaVistaLogin);
        ControladorIS nuevoControladorLogin = new ControladorIS(nuevoModeloLogin, nuevaVistaLogin);
        nuevaVistaLogin.setControlador(nuevoControladorLogin);
        nuevaVistaLogin.setVisible(true);
    }


    public void iniciarVista() {
        vista.mostrarEmpleado();
    }
}

