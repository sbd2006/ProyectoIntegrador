package Vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class VistaP extends JFrame {
    public JPanel producto;
    public JTextField categoriatext;
    public JTextField preciotext;
    public JTextField cantidadtext;
    public JTable table1;
    public JButton mostrarButton;
    public JButton editarButton;
    public JButton eliminarButton;
    public JButton guardarButton;
    public JTextField nombretext;
    public JButton regresarButton;
    public JTextField Idtext;
    public JLabel Id;

    public DefaultTableModel modTabla;

    public VistaP() {
        modTabla = new DefaultTableModel(new String[]{"Id_producto","Nombre","Categoria","Precio","Stock"}, 0);
        table1.setModel(modTabla);

        setContentPane(producto);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }
    public void mostrarVista() {
        this.setVisible(true);
    }

}
