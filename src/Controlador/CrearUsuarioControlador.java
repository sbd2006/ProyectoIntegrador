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
        String nombre = vista.IngresoNombre.getText().trim();
        String apellido = vista.IngresoApellido.getText().trim();
        String telefono = vista.IngresoTelefono.getText().trim();
        String direccion = vista.IngresoDireccion.getText().trim();
        String usuario = vista.IngresoUsuario.getText().trim();
        String contrasena = new String(vista.IngresoContraseña.getPassword()).trim();

        if (nombre.isEmpty() || apellido.isEmpty() || telefono.isEmpty() ||
                direccion.isEmpty() || usuario.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Por favor, completa todos los campos.", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        CrearUsuario nuevoUsuario = new CrearUsuario(nombre, apellido, telefono, direccion, usuario, contrasena);

        if (dao.guardarUsuario(nuevoUsuario)) {
            JOptionPane.showMessageDialog(vista, "Usuario creado exitosamente.");
            vista.regresarAInicioSesion();
            vista.dispose();
        }
    }
}
