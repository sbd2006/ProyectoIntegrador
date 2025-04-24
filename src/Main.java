import Controlador.ControladorIS;
import Modelo.InicioSesion;

public class Main {
    public static void main(String[] args) {
        InicioSesion vista = new InicioSesion();
        ControladorIS controlador = new ControladorIS(vista);

        vista.setControlador(controlador);

        vista.setContentPane(vista.Mensaje);
        vista.setDefaultCloseOperation(javax.swing.JFrame.DISPOSE_ON_CLOSE);
        vista.setVisible(true);
        vista.pack();
        vista.setSize(1920, 1080);
    }
}

