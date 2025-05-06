package Controlador;

import Modelo.ReporteVentaDAO;
import Vista.AdministradorVista;
import Vista.ReporteVentaVista;

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
        vista.getReporteVentaButton().addActionListener(e -> abrirVistaReporteVenta());


    }

    private void abrirVistaReporteVenta() {
        ReporteVentaVista vista = new ReporteVentaVista();
        ReporteVentaDAO dao = new ReporteVentaDAO();
        ReporteVentaControlador controlador = new ReporteVentaControlador(vista, dao);
        vista.setVisible(true);
    }


}

