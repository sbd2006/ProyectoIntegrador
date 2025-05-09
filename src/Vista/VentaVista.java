package Vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class VentaVista extends JFrame {
    public JPanel Venta;
    public JTable tableCatalogo;
    public JTable tableVenta;
    public JTextField IdProducto, CantidadP, PrecioUnitario, FechaVenta, Total;
    public JButton finalizarVenta, regresarButton, agregarProductoButton, mostrarButton, eliminarButton;
    public JLabel Id, CANTIDAD_PRODUCTO;
    public JTextField nombreCliente;
    private JTextField Direccion;
    private JTextField Telefono;
    public DefaultTableModel modTablaCatalogo;
    public DefaultTableModel modTablaVenta;

    public VentaVista() {
        setTitle("Gestión de Ventas");
        setContentPane(Venta);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        pack();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        IdProducto.setEditable(false);
        PrecioUnitario.setEditable(false);
        FechaVenta.setEditable(false);
        Total.setEditable(false);
        finalizarVenta.setEnabled(false);


        String[] columnas = {"ID", "Nombre", "Categoría", "Precio", "Stock"};
        modTablaCatalogo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableCatalogo.setModel(modTablaCatalogo);

        String[] columnasVenta = {"ID", "Nombre", "Cantidad", "Precio Unitario", "Total"};
        modTablaVenta = new DefaultTableModel(columnasVenta, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableVenta.setModel(modTablaVenta);


    }
    public JTextField getNombreCliente() {
        return nombreCliente;
    }


    public JTextField getDireccion() {
        return Direccion;
    }

    public JTextField getTelefono() {
        return Telefono;
    }
}
