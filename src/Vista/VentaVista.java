package Vista;

import javax.swing.*;
import java.awt.*;

public class VentaVista extends JFrame {
    public JPanel Venta;
    public JTable tableCatalogo;
    public JTable tablePedido;
    public JTextField IdProducto, CantidadP, PrecioUnitario, Descripcion, IdPedido, FechaVenta, Total;
    public JButton confirmarPedidoButton, regresarButton, agregarPedidoButton, agregarProductoButton, mostrarCatalogoButton;
    public JLabel Id,CANTIDAD_PRODUCTO;

    public VentaVista() {
        setTitle("Gestión de Ventas");
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelFormulario = new JPanel(new GridLayout(4, 4));
        IdProducto = new JTextField();
        CantidadP = new JTextField();
        PrecioUnitario = new JTextField();
        Descripcion = new JTextField();
        IdPedido = new JTextField();
        FechaVenta = new JTextField();
        Total = new JTextField();

        panelFormulario.add(new JLabel("ID Producto"));
        panelFormulario.add(IdProducto);
        panelFormulario.add(new JLabel("Cantidad"));
        panelFormulario.add(CantidadP);
        panelFormulario.add(new JLabel("Precio Unitario"));
        panelFormulario.add(PrecioUnitario);
        panelFormulario.add(new JLabel("Descripción"));
        panelFormulario.add(Descripcion);
        panelFormulario.add(new JLabel("ID Pedido"));
        panelFormulario.add(IdPedido);
        panelFormulario.add(new JLabel("Fecha Pedido"));
        panelFormulario.add(FechaVenta);
        panelFormulario.add(new JLabel("Total"));
        panelFormulario.add(Total);

        JPanel panelBotones = new JPanel();
        confirmarPedidoButton = new JButton("Confirmar Venta");
        regresarButton = new JButton("Regresar");
        agregarPedidoButton = new JButton("Agregar Venta");
        agregarProductoButton = new JButton("Agregar Producto");
        mostrarCatalogoButton = new JButton("Mostrar Catálogo");
        panelBotones.add(confirmarPedidoButton);
        panelBotones.add(regresarButton);
        panelBotones.add(agregarPedidoButton);
        panelBotones.add(agregarProductoButton);
        panelBotones.add(mostrarCatalogoButton);

        tableCatalogo = new JTable();
        tablePedido = new JTable();

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JScrollPane(tableCatalogo), new JScrollPane(tablePedido));
        splitPane.setResizeWeight(0.5);

        add(panelFormulario, BorderLayout.NORTH);
        add(splitPane, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }
    public void mostrarVenta() {
        setContentPane(Venta);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        pack();
        setSize(1920, 1080);
    }
}
