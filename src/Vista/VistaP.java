package Vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class VistaP extends JFrame {
    private JPanel producto;
    private JTextField preciotext;
    private JTextField cantidadtext;
    private JTable tablaProductos;
    private JButton mostrarButton;
    private JButton editarButton;
    private JButton eliminarButton;
    private JButton guardarButton;
    private JTextField nombretext;
    private JButton regresarButton;
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

    public JTable getTabla() {
        return tablaProductos;
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

    public JComboBox getComboBoxCategoria() {
        return categoriatext;
    }

    public JButton getDeleteCategory() {
        return deleteCategory;
    }



    public VistaP() {
        modTabla = new DefaultTableModel(new String[]{"Codigo","Nombre","Categoria","Precio","Stock"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaProductos.setModel(modTabla);
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
