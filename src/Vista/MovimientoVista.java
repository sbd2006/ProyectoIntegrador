package Vista;

import javax.swing.*;
import java.time.LocalDate;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class MovimientoVista extends JFrame {

    private JComboBox<String> comboProductos;
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

    public JComboBox<String> getComboProductos() {
        return comboProductos;
    }

    public void mostrarVista(){
        setContentPane(panelM);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
    public void cargarTablaMovimiento(List<String[]> datos) {
        DefaultTableModel modelo = new DefaultTableModel(new String[]{"Producto", "Cantidad", "Fecha", "Obs"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (String[] fila : datos) {
            modelo.addRow(fila);
        }
        table1.setModel(modelo);
    }

    public void cargarTablaDocumento(List<String[]> datos) {
        DefaultTableModel modelo = new DefaultTableModel(new String[]{"ID", "Fecha", "Nro Documento"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (String[] fila : datos) {
            modelo.addRow(fila);
        }
        table2.setModel(modelo);
    }


    public void setComboBoxItems(List<String> nombresProductos) {
        comboProductos.removeAllItems();
        for (String nombre : nombresProductos) {
            comboProductos.addItem(nombre);
        }
    }
}