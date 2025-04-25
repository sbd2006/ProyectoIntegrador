package Vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class VistaP extends JFrame {
    private JPanel producto;
    private JTextField categoriatext;
    private JTextField preciotext;
    private JTextField cantidadtext;
    private JTable table1;
    private JButton mostrarButton;
    private JButton editarButton;
    private JButton eliminarButton;
    private JButton guardarButton;
    private JTextField nombretext;
    private JButton regresarButton;
    private JLabel Id;
    private JTextField Idtext;

    public JTextField getCategoriatext() {
        return categoriatext;
    }

    public JTextField getPreciotext() {
        return preciotext;
    }

    public JTextField getCantidadtext() {
        return cantidadtext;
    }

    public JTextField getNombretext() {
        return nombretext;
    }

    public JTextField getIdtext() {
        return Idtext;
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

    public JButton getRegresarButton() {
        return regresarButton;
    }

    public VistaP() {
        setContentPane(producto);
        setTitle("Gestión de Productos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // 👇 Asegúrate de que la tabla tenga columnas desde el inicio
        String[] columnas = {"Id", "Nombre", "Categoria", "Precio", "Cantidad_Actual"};
        DefaultTableModel modeloInicial = new DefaultTableModel(null, columnas);
        table1.setModel(modeloInicial);

        pack();
        setLocationRelativeTo(null);
    }

    public void mostrarProducto() {
        setVisible(true);
    }

    public void actualizarTabla(DefaultTableModel nuevoModelo) {
        table1.setModel(nuevoModelo);
    }


    // Puedes agregar métodos opcionales para limpiar campos si gustas
    public void limpiarCampos() {
        Idtext.setText("");
        nombretext.setText("");
        categoriatext.setText("");
        preciotext.setText("");
        cantidadtext.setText("");
    }

    public DefaultTableModel getModeloTabla() {
        return (DefaultTableModel) table1.getModel();
    }
}
