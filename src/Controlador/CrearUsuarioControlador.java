package Controlador;

import Modelo.CrearUsuario;
import Modelo.CrearUsuarioDAO;
import Vista.CrearUsuarioVista;

import javax.swing.*;

public class CrearUsuarioControlador extends JFrame {
    private final CrearUsuarioVista vista;
    private final CrearUsuarioDAO dao;

    public CrearUsuarioControlador(CrearUsuarioVista vista, CrearUsuarioDAO dao) {
        this.vista = vista;
        this.dao = dao;

        this.vista.BotonCrear.addActionListener(e -> crearUsuario());
        this.vista.BotonRegresar.addActionListener(e -> vista.regresarAInicioSesion());
    }

    private void crearUsuario() {
        CrearUsuario usuario = new CrearUsuario(
                vista.IngresoNombre.getText(),
                vista.IngresoApellido.getText(),
                vista.IngresoTelefono.getText(),
                vista.IngresoDireccion.getText(),
                vista.IngresoUsuario.getText(),
                new String(vista.IngresoContraseña.getPassword())
        );

        if (dao.guardarUsuario(usuario)) {
            JOptionPane.showMessageDialog(vista, "Usuario creado exitosamente.");
            vista.regresarAInicioSesion();
            vista.dispose();
        }
    }
}
