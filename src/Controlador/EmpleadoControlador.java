package Controlador;

import Vista.EmpleadoVista;
import Vista.InicioSesionVista;
import Vista.AdministracionVentasVista;

public class EmpleadoControlador {

    private final EmpleadoVista vista;

    public EmpleadoControlador(EmpleadoVista vista) {
        this.vista = vista;
        initController();
    }

    private void initController() {
        vista.realizarVentaButton.addActionListener(e -> mostrarVentas());
        vista.cerrarSesionButton.addActionListener(e -> cerrarSesion());
    }

    private void mostrarVentas() {
        AdministracionVentasView vistaVentas = new AdministracionVentasView();
        vistaVentas.mostrarVista();
        vista.dispose();
    }

    private void cerrarSesion() {
        InicioSesionView inicio = new InicioSesionView();
        inicio.mostrarVista();
        vista.dispose();
    }

    public void iniciarVista() {
        vista.mostrarVista();
    }
}
