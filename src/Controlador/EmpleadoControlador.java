package Controlador;

import Modelo.ReporteVentaDAO;
import Modelo.ModeloIS;
import Modelo.VentaDAO;
import Vista.*;

public class EmpleadoControlador {

    private final EmpleadoVista vista;
    private final String nombreUsuario;
    private final int idEmpleado;

    public EmpleadoControlador(EmpleadoVista vista, String nombreUsuario, int idEmpleado) {
        this.vista = vista;
        this.nombreUsuario = nombreUsuario;
        this.idEmpleado = idEmpleado;
        initController();
    }

    private void initController() {
        vista.realizarVentaButton.addActionListener(e -> mostrarVenta());
        vista.cerrarSesionButton.addActionListener(e -> cerrarSesion());
        vista.getReporteVentaButton().addActionListener(e -> abrirVistaReporteVenta());
        vista.setNombreUsuario(nombreUsuario);
        vista.getConsultarVentasButton().addActionListener(e -> {
            AdministracionVentasVista ventasVista = new AdministracionVentasVista(vista);
            VentaDAO ventaDAO = new VentaDAO();
            AdministracionVentasControlador ventasControlador = new AdministracionVentasControlador(ventasVista, ventaDAO);
            ventasControlador.iniciarVista();
            vista.setVisible(false);

        });
    }

    private void mostrarVenta() {
        VentaVista vistaVenta = new VentaVista();
        VentaDAO dao = new VentaDAO();
        VentaControlador controlador = new VentaControlador(vistaVenta, dao, vista, idEmpleado);
        vistaVenta.setVisible(true);
        vista.dispose();
    }

    private void abrirVistaReporteVenta() {
        ReporteVentaVista vistaReporte = new ReporteVentaVista();
        ReporteVentaDAO dao = new ReporteVentaDAO();
        ReporteVentaControlador controlador = new ReporteVentaControlador(vistaReporte, dao);
        vistaReporte.setVisible(true);
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