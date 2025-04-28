package Controlador;

import Modelo.CrearUsuarioDAO;
import Vista.CrearUsario.CrearUsuarioVista;

public class CrearUsuarioControlador {
    private CrearUsuarioVista vista;
    private CrearUsuarioDAO dao;

    public CrearUsuarioControlador(CrearUsuarioVista vista) {
        this.vista = vista;
        this.dao = new CrearUsuarioDAO();

        vista.BotonCrear.addActionListener(e -> crearUsuario());
    }
    private void crearUsuario() {
        Modelo.CrearUsuario usuario = new Modelo.CrearUsuario(
                vista.IngresoNombre.getText(),
                vista.IngresoApellido.getText(),
                vista.IngresoTelefono.getText(),
                vista.IngresoDireccion.getText(),
                vista.IngresoUsuario.getText(),
                new String(vista.IngresoContraseña.getPassword())
        );
        dao.insertarUsuario(usuario);
    }
}
