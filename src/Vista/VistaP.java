package Vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class VistaP extends JFrame {
    private JPanel producto;
    private JTextField preciotext;
    private JTextField cantidadtext;
    private JTable table1;
    private JButton mostrarButton;
    private JButton editarButton;
    private JButton eliminarButton;
    private JButton guardarButton;
    private JTextField nombretext;
    private JButton regresarButton;
    private JTextField Idtext;
    private JLabel Id;
    private JComboBox categoriatext;
    public JButton newButton;
    private JButton deleteCategory;
    public DefaultTableModel modTabla;

    public JTextField getPreciotext() {
        return preciotext;
    }

    public JTextField getCantidadtext() {
        return cantidadtext;
    }

    public JTable getTable1() {
        return table1;
    }

    public JButton getMostrarButton() {
        return mostrarButton;
    }

    public JButton getEditarButton() {
        return editarButton;
    }

    public JButton getEliminarButton() {
        return eliminarButton;
    }

    public JButton getGuardarButton() {
        return guardarButton;
    }

    public JTextField getNombretext() {
        return nombretext;
    }

    public JButton getRegresarButton() {
        return regresarButton;
    }

    public JTextField getIdtext() {
        return Idtext;
    }

    public JComboBox getComboBoxCategoria() {
        return categoriatext;
    }

    public JButton getDeleteCategory() {
        return deleteCategory;
    }



    public VistaP() {
        modTabla = new DefaultTableModel(new String[]{"Id_producto","Nombre","Categoria","Precio","Stock"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

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
