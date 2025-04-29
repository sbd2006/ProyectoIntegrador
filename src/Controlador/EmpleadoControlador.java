package Controlador;

import Vista.EmpleadoVista;
import Vista.VistaIS;
import Vista.AdministracionVentasVista;

public class EmpleadoControlador {

    private final EmpleadoVista vista;
    private final String nombreUsuario; // Hacemos final el nombre del usuario

    public EmpleadoControlador(EmpleadoVista vista, String nombreUsuario) {
        this.vista = vista;
        this.nombreUsuario = nombreUsuario;
        initController();
    }

    private void initController() {
        vista.realizarVentaButton.addActionListener(e -> mostrarVentas());
        vista.cerrarSesionButton.addActionListener(e -> cerrarSesion());
        vista.setNombreUsuario(nombreUsuario); // Aquí actualizamos el nombre en la vista
    }

    private void mostrarVentas() {
        AdministracionVentasVista vistaVentas = new AdministracionVentasVista();
        vistaVentas.mostrarVista();
        vista.dispose();
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

