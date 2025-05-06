package Vista;

import javax.swing.*;
import java.time.LocalDate;

public class MovimientoVista extends JFrame {

    private JTextField ProductoId;
    private JTextField Cantidad;
    private JComboBox<String> tipoDocu;
    private JButton Regresar;
    private JButton registrarButton;
    private JPanel panelM;
    private JTextArea obs;
    private JTextField fecha;
    private JTextField Ndocumento;
    private AdministradorVista administradorVista;

    public MovimientoVista(){
        tipoDocu.addItem("Entrada");
        tipoDocu.addItem("Salida");
        tipoDocu.addItem("Ajuste");

        fecha.setText(LocalDate.now().toString());
        fecha.setEditable(false);
    }

    public JTextArea getObs() {
        return obs;
    }

    public JTextField getFecha() {
        return fecha;
    }

    public JTextField getNdocumento() {
        return Ndocumento;
    }

    public void setAdministradorVista(AdministradorVista administradorVista) {
        this.administradorVista = administradorVista;
    }


    public JButton getRegistrarButton() {
        return registrarButton;
    }


    public JTextField getProductoId() {
        return ProductoId;
    }

    public JTextField getCantidad() {
        return Cantidad;
    }

    public JComboBox<String> getTipoDocu() {
        return tipoDocu;
    }


    public JButton getRegresar() {
        return Regresar;
    }

    public int getDocumentoId() {
        return 1;
    }

    public void mostrarVista(){
        setContentPane(panelM);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }
}