package Controlador;

import Vista.AdministradorVista;

public class AdministradorControlador {
    private final AdministradorVista vista;
    private final String nombreUsuario;

    public AdministradorControlador(AdministradorVista vista, String nombreUsuario) {
        this.vista = vista;
        this.nombreUsuario = nombreUsuario;
    }

    public void iniciarVista() {
        vista.setNombreUsuario(nombreUsuario);
        vista.setVisible(true);
    }
}

