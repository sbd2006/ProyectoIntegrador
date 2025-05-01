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
    public DefaultTableModel modTablaCatalogo;
    public DefaultTableModel modTablaVenta;

    public VentaVista() {
        setTitle("Gestión de Ventas");
        setContentPane(Venta);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1920, 1080);
        setLocationRelativeTo(null);
        finalizarVenta.setEnabled(false);

        String[] columnas = {"ID", "Nombre", "Categoría", "Precio", "Stock"};
        modTablaCatalogo = new DefaultTableModel(columnas, 0);
        tableCatalogo.setModel(modTablaCatalogo);

        String[] columnasVenta = {"ID", "Nombre", "Cantidad", "Precio Unitario", "Total"};
        modTablaVenta = new DefaultTableModel(columnasVenta, 0);
        tableVenta.setModel(modTablaVenta);

    }
}
