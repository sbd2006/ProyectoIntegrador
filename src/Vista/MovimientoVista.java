package Vista;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MovimientoVista extends JFrame {

    private JTextField ProductoId;
    private JTextField Cantidad;
    private JTextField Observacion;
    private JComboBox comboBox1;
    private JButton Regresar;
    private JButton registrarButton;
    private JPanel panelM;
    private AdministradorVista administradorVista;

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

    public JTextField getObservacion() {
        return Observacion;
    }

    public JButton getRegresar() {
        return Regresar;
    }

    public int getTipoMovimientoSeleccionado() {
        return comboBox1.getSelectedIndex() + 1;
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
