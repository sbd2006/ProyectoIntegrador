import Controlador.ControladorIS;
import Modelo.ModeloIS;
import Vista.VistaIS;
import Modelo.ModeloP;

import javax.swing.table.DefaultTableModel;

public class Main {
    public static void main(String[] args) {
        VistaIS vista = new VistaIS();
        ModeloIS modelo = new ModeloIS(vista);
        ControladorIS controlador = new ControladorIS(modelo, vista);

        vista.setControlador(controlador);

        vista.setContentPane(vista.Mensaje);
        vista.setDefaultCloseOperation(javax.swing.JFrame.DISPOSE_ON_CLOSE);
        vista.setVisible(true);
        vista.pack();
        vista.setSize(1920, 1080);
    }
}