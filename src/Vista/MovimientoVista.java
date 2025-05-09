package Vista;

import javax.swing.*;
import java.time.LocalDate;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class MovimientoVista extends JFrame {

    private JTextField ProductoId;
    private JTextField Cantidad;
    private JComboBox<String> tipoDocu;
    private JButton Regresar;
    private JButton registrarButton;
    private JPanel panelM;
    private JTextField obs;
    private JTextField fecha;
    private JTextField Ndocumento;
    private JTable table1;
    private JTable table2;
    private AdministradorVista administradorVista;

    public MovimientoVista(){
        fecha.setText(LocalDate.now().toString());
        fecha.setEditable(false);
        mostrarVista();
    }

    public JTextField getObs() {
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

    public JTable getTablaMovimiento() {
        return table1;
    }

    public JTable getTablaDocumento() {
        return table2;
    }

    public void mostrarVista(){
        setContentPane(panelM);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }
    public void cargarTablaMovimiento(List<String[]> datos) {
        DefaultTableModel modelo = new DefaultTableModel(new String[]{"Id_producto", "Cantidad", "Fecha", "Obs"}, 0);
        for (String[] fila : datos) {
            modelo.addRow(fila);
        }
        table1.setModel(modelo);
    }

    public void cargarTablaDocumento(List<String[]> datos) {
        DefaultTableModel modelo = new DefaultTableModel(new String[]{"ID", "Fecha", "Nro Documento"}, 0);
        for (String[] fila : datos) {
            modelo.addRow(fila);
        }
        table2.setModel(modelo);
    }

}