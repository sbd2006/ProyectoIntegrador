package Controlador;

import Modelo.*;
import Vista.Administrador;
import Vista.Empleado;
import Vista.VistaIS;
import Vista.CrearUsario.CrearUsuarioVista;

import javax.swing.*;

public class ControladorIS {
    private ModeloIS modelo;
    private VistaIS vista;

    public ControladorIS(ModeloIS modelo, VistaIS vista) {
        this.modelo = modelo;
        this.vista = vista;
    }


    public void Validacion() {
        Conexion conX = new Conexion();
        conX.conectar();
        String userType = modelo.validacionSQL();

        if ("Administrador".equals(userType)) {
            JOptionPane.showMessageDialog(null, "Bienvenido Administrador");
            Administrador enlace = new Administrador();
            enlace.mostrarAdministrador();
            vista.dispose();
        } else if ("Usuario".equals(userType)) {
            JOptionPane.showMessageDialog(null, "Bienvenido Cliente");
            Empleado enlace = new Empleado();
            enlace.mostrarEmpleado();
            vista.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Las credenciales no son correctas");
        }
    }

    public void mostrarCrearUsuario() {
        CrearUsuarioVista vistaCU = new CrearUsuarioVista();
        new CrearUsuarioControlador(vistaCU);
        vistaCU.setVisible(true);
    }
}
