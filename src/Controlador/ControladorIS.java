package Controlador;

import Modelo.*;
import Vista.AdministradorVista;
import Vista.EmpleadoVista;
import Vista.VistaIS;
import Vista.CrearUsuarioVista;


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

        String nombre = modelo.getNombreUsuario(); // ← Asegúrate de tener esto

        if ("Administrador".equals(userType)) {
            JOptionPane.showMessageDialog(null, "Bienvenido Administrador");
            AdministradorVista vistaAdmin = new AdministradorVista();
            AdministradorControlador controladorAdmin = new AdministradorControlador(vistaAdmin, nombre);
            controladorAdmin.iniciarVista();
            vista.dispose();

        } else if ("Usuario".equals(userType)) {
            JOptionPane.showMessageDialog(null, "Bienvenido Empleado");
            EmpleadoVista vistaEmpleado = new EmpleadoVista();
            EmpleadoControlador controladorEmpleado = new EmpleadoControlador(vistaEmpleado, nombre);
            controladorEmpleado.iniciarVista();
            vista.dispose();

        } else {
            JOptionPane.showMessageDialog(null, "Las credenciales no son correctas");
        }
    }


    public void mostrarCrearUsuario() {
        CrearUsuarioVista vistaCU = new CrearUsuarioVista();
        CrearUsuarioDAO dao = new CrearUsuarioDAO();
        new CrearUsuarioControlador(vistaCU, dao);
        vistaCU.setVisible(true);
    }

}
