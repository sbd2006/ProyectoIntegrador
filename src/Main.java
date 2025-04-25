import Controlador.ControladorIS;
import Modelo.ModeloIS;
import Vista.VistaIS;
import Modelo.ModeloP;

import javax.swing.table.DefaultTableModel;

public class Main {
    public static void main(String[] args) {
            try {
                ModeloP modelo = new ModeloP();
                DefaultTableModel tabla = modelo.mostrarProductos();
                System.out.println("Número de filas encontradas: " + tabla.getRowCount());
            } catch (Exception e) {
                e.printStackTrace(); // Esto nos da la pista real
            }

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
