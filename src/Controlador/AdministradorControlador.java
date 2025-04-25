package Controlador;

import Vista.AdministradorVista;
import Vista.VistaIS;
import Vista.VistaP;
import Vista.RolSelectionVista;

public class AdministradorControlador {

    private final AdministradorVista vista;
    private final String nombreUsuario;

    public AdministradorControlador(AdministradorVista vista, String nombreUsuario) {
        this.vista = vista;
        this.nombreUsuario = nombreUsuario;
        initController();
    }

    private void initController() {
        vista.setNombreUsuario(nombreUsuario); // ← Esto actualiza el JLabel con el nombre del usuario
        vista.productoButton.addActionListener(e -> enlazarProducto());
        vista.cerrarSesionButton.addActionListener(e -> cerrarSesion());
        vista.gestionDeUsuariosButton.addActionListener(e -> enlazarGestionUsuarios());
    }

    private void enlazarProducto() {
        VistaP producto = new VistaP();
        producto.mostrarVista();
        vista.dispose();
    }

    private void cerrarSesion() {
        VistaIS inicio = new VistaIS();
        inicio.mostrarVista();
        vista.dispose();
    }

    private void enlazarGestionUsuarios() {
        RolSelectionVista rol = new RolSelectionVista();
        rol.mostrarVista();
        vista.dispose();
    }

    public void iniciarVista() {
        vista.mostrarVista();
    }
}

