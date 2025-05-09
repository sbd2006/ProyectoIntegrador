package Controlador;


import Modelo.ModeloIS;
import Modelo.ReporteVentaDAO;

import Modelo.VentaDAO;
import Vista.EmpleadoVista;
import Vista.ReporteVentaVista;
import Vista.VistaIS;
import Vista.VentaVista;

public class EmpleadoControlador {

    private final EmpleadoVista vista;
    private final String nombreUsuario; // Hacemos final el nombre del usuario

    public EmpleadoControlador(EmpleadoVista vista, String nombreUsuario) {
        this.vista = vista;
        this.nombreUsuario = nombreUsuario;
        initController();
    }

    private void initController() {

        vista.realizarVentaButton.addActionListener(e -> mostrarVenta());
        vista.cerrarSesionButton.addActionListener(e -> cerrarSesion());
        vista.getReporteVentaButton().addActionListener(e -> abrirVistaReporteVenta());
        vista.setNombreUsuario(nombreUsuario);
    }
    private void abrirVistaReporteVenta() {
        ReporteVentaVista vista = new ReporteVentaVista();
        ReporteVentaDAO dao = new ReporteVentaDAO();
        ReporteVentaControlador controlador = new ReporteVentaControlador(vista, dao);
        vista.setVisible(true);
    }

    private void mostrarVenta() {
        VentaVista vistaV = new VentaVista();
        VentaDAO dao = new VentaDAO();
        VentaControlador controlador = new VentaControlador(vistaV, dao, this.vista);
        vistaV.setVisible(true);
        vista.dispose();
    }

    private void cerrarSesion() {
        vista.dispose();
        VistaIS nuevaVistaLogin = new VistaIS();
        ModeloIS nuevoModeloLogin = new ModeloIS(nuevaVistaLogin);
        ControladorIS nuevoControladorLogin = new ControladorIS(nuevoModeloLogin, nuevaVistaLogin);
        nuevaVistaLogin.setControlador(nuevoControladorLogin);
        nuevaVistaLogin.setVisible(true);
    }

    public void iniciarVista() {
        vista.setVisible(true);
    }
}

