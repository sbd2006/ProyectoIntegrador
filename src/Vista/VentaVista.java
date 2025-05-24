package Vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class VentaVista extends JFrame {
    public JPanel Venta;
    public JTable tableCatalogo;
    public JTable tableVenta;
    public JTextField IdProducto, CantidadP, PrecioUnitario, FechaVenta, Total;
    public JButton finalizarVenta, regresarButton, agregarProductoButton, mostrarButton, eliminarButton;
    public JTextField nombreCliente;
    private JTextField Direccion;
    private JTextField Telefono;
    private JLabel Id;
    private JLabel CANTIDAD_PRODUCTO;
    private JTextField DineroRecibido;
    private JTextField Cambio;
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

        DineroRecibido.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                JTextField field = DineroRecibido;
                int pos = field.getCaretPosition();
                String texto = field.getText().replace(".", "");

                if (!texto.isEmpty() && texto.matches("\\d+")) {
                    try {
                        long valor = Long.parseLong(texto);
                        String formateado = String.format("%,d", valor).replace(",", ".");
                        field.setText(formateado);
                        if (pos > formateado.length()) pos = formateado.length();
                        field.setCaretPosition(pos);
                    } catch (NumberFormatException ignored) {}
                }
            }
        });


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

    public JTextField getDineroRecibido() {
        return DineroRecibido;
    }

    public JTextField getCambio() {
        return Cambio;
    }
}
