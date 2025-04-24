package Controlador;

import Vista.AdministradorVista;
import Vista.InicioSesionView;
import Vista.ProductoView;
import Vista.RolselectionView;

public class AdministradorControlador {

    private final AdministradorVista vista;

    public AdministradorControlador(AdministradorVista vista) {
        this.vista = vista;
        initController();
    }

    private void initController() {
        vista.productoButton.addActionListener(e -> enlazarProducto());
        vista.cerrarSesionButton.addActionListener(e -> cerrarSesion());
        vista.gestionDeUsuariosButton.addActionListener(e -> enlazarGestionUsuarios());
    }

    private void enlazarProducto() {
        ProductoView producto = new ProductoView();
        producto.mostrarVista();
        vista.dispose();
    }

    private void cerrarSesion() {
        InicioSesionView inicio = new InicioSesionView();
        inicio.mostrarVista();
        vista.dispose();
    }

    private void enlazarGestionUsuarios() {
        RolselectionView rol = new RolselectionView();
        rol.mostrarVista();
        vista.dispose();
    }

    public void iniciarVista() {
        vista.mostrarVista();
    }
}
