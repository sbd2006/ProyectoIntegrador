import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.MouseAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

public class Venta extends JFrame {
    private JTable Catalogo;
    private JTextField PrecioUnitario;
    private JPanel Pedido;
    private JTable tableCatalogo;
    private JTable tablePedido;
    private JTextField IdProducto;
    private JButton confirmarPedidoButton;
    private JButton regresarButton;
    private JTextField CantidadP;
    private JTextField IdPedido;
    private JTextField FechaPedido;
    private JTextField Descripcion;
    private JTextField Total;
    private JButton agregarPedidoButton;
    private JButton agregarProductoButton;
    private JButton mostrarCatalogoButton;
    private JButton mostrarPedidoButton;
    private JLabel ID_PEDIDO;
    private JLabel Id;
    private JLabel FECHA_PEDIDO;
    private JLabel CANTIDAD_PRODUCTO;
    private JLabel DESCRIPCION;
    private JLabel PRECIO_UNITARIO;
    private JLabel TOTAL;
    private JTextField Total_Producto;
    private JLabel TOTAL_PRODUCTO;
    private JButton mostrarButton;


    Connection conexion;
    PreparedStatement ps;
    String[] campoCatalogo = {"Id", "Nombre", "Categoria", "Precio", "Cantidad_Actual"};
    String[] registros = new String[10];
    DefaultTableModel modTabla = new DefaultTableModel(null, campoCatalogo);
    Statement st;
    ResultSet rs;
    List<Detalle_Pedido> productosV = new ArrayList<>();

    public Venta() {

        LocalDate fechaActual = LocalDate.now();
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        FechaPedido.setText(fechaActual.format(formatoFecha));

        confirmarPedidoButton.setEnabled(false);

        agregarProductoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarProducto();
                actualizarTotal();
            }
        });
        agregarPedidoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarPedido();
                confirmarPedidoButton.setEnabled(true);
            }
        });
        CantidadP.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                calcularTotalP();
            }
        });

        PrecioUnitario.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                calcularTotalP();
            }
        });
        regresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                regresarCliente();
                dispose();
            }
        });
        mostrarCatalogoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MostrarCatalogo();
            }
        });

        mostrarPedidoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MostrarPedido();

            }
        });
        confirmarPedidoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            dispose();
            }
        });
        PrecioUnitario.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                calcularTotalP();
            }
        });
        Total.setEditable(false);
        Total_Producto.setEditable(false);
        IdProducto.setEditable(false);
        FechaPedido.setEditable(false);
        PrecioUnitario.setEditable(false);
        IdPedido.setEditable(false);
        tableCatalogo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int SelectRow = tableCatalogo.getSelectedRow();
                if (SelectRow != -1){
                    String idProducto = tableCatalogo.getValueAt(SelectRow,0).toString();
                    IdProducto.setText(idProducto);
                }

            }

        });
//        confirmarPedidoButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                mostrarPago();
//            }
//        });
        tableCatalogo.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()){
                    int selectedRow = tableCatalogo.getSelectedRow();
                    if (selectedRow != -1){
                        Object precio = tableCatalogo.getValueAt(selectedRow,3);
                        PrecioUnitario.setText(precio != null ? precio.toString() : "");
                    }
                }
            }
        });
    }

    class Detalle_Pedido {
        String ID_PEDIDO;
        String Id;
        String CANTIDAD_PRODUCTO;
        String DESCRIPCION;
        String PRECIO_UNITARIO;
        String TOTAL_PRODUCTO;

        public Detalle_Pedido(String ID_PEDIDO, String Id, String CANTIDAD_PRODUCTO, String DESCRIPCION, String PRECIO_UNITARIO, String TOTAL_PRODUCTO) {
            this.ID_PEDIDO = ID_PEDIDO;
            this.Id = Id;
            this.CANTIDAD_PRODUCTO = CANTIDAD_PRODUCTO;
            this.DESCRIPCION = DESCRIPCION;
            this.PRECIO_UNITARIO = PRECIO_UNITARIO;
            this.TOTAL_PRODUCTO = TOTAL_PRODUCTO;

        }
    }

    void conectar(){
        try {
            conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/PostresMariaJose", "root", "Santi104");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    void agregarProducto() {

        conectar();

        String ID_PEDIDO = IdPedido.getText().trim();
        String Id = IdProducto.getText().trim();
        String CANTIDAD_PRODUCTO = CantidadP.getText().trim();
        String DESCRIPCION = Descripcion.getText().trim();
        String PRECIO_UNITARIO = PrecioUnitario.getText().trim();
        String TOTAL_PRODUCTO = Total_Producto.getText().trim().replace(",", ".");


        Detalle_Pedido producto1 = new Detalle_Pedido(ID_PEDIDO, Id, CANTIDAD_PRODUCTO, DESCRIPCION, PRECIO_UNITARIO, TOTAL_PRODUCTO);
        productosV.add(producto1);

        JOptionPane.showMessageDialog(null, "Producto agregado al pedido");

    }


    void agregarPedido() {
        conectar();
        int id_pedi = -1;
        try {

            conexion.setAutoCommit(false);


            String sqlPEDIDO = "INSERT INTO PEDIDO (FECHA_PEDIDO, TOTAL) VALUES (?,?)";
            ps = conexion.prepareStatement(sqlPEDIDO, Statement.RETURN_GENERATED_KEYS);


            String FECHA_PEDIDO = FechaPedido.getText();
            String TOTAL = Total.getText().trim().replace(",", ".");


            ps.setString(1, FECHA_PEDIDO);
            ps.setString(2, TOTAL);

            ps.executeUpdate();
            rs = ps.getGeneratedKeys();

            if (rs.next()) {
                id_pedi = rs.getInt(1);
                IdPedido.setText(String.valueOf(id_pedi));
            } else {
                throw new SQLException("Error al obtener el ID_PEDIDO generado");
            }


            String sqlDETALLE_PEDIDO = "INSERT INTO DETALLE_PEDIDO (ID_PEDIDO, Id, CANTIDAD_PRODUCTO, DESCRIPCION, PRECIO_UNITARIO, TOTAL_PRODUCTO) VALUES (?,?,?,?,?,?)";
            ps = conexion.prepareStatement(sqlDETALLE_PEDIDO);

            for (Detalle_Pedido producto1 : productosV) {
                ps.setInt(1, id_pedi);
                ps.setString(2, producto1.Id);
                ps.setString(3, producto1.CANTIDAD_PRODUCTO);
                ps.setString(4, producto1.DESCRIPCION);
                ps.setString(5, producto1.PRECIO_UNITARIO);
                ps.setString(6, producto1.TOTAL_PRODUCTO);
                ps.addBatch();
            }

            int[] filasinsertadas = ps.executeBatch();

            if (filasinsertadas.length > 0) {

                String sqlActualizarCantidad = "UPDATE Producto SET Cantidad_Actual = Cantidad_Actual - ? WHERE Id = ?";
                ps = conexion.prepareStatement(sqlActualizarCantidad);

                for (Detalle_Pedido producto1 : productosV) {
                    ps.setInt(1, Integer.parseInt(producto1.CANTIDAD_PRODUCTO));
                    ps.setString(2, producto1.Id);
                    ps.addBatch();
                }


                int[] filasActualizadas = ps.executeBatch();
                if (filasActualizadas.length == productosV.size()) {
                    conexion.commit();
                    JOptionPane.showMessageDialog(null, "El pedido se guardo con exito!");
                    productosV.clear();
                    confirmarPedidoButton.setEnabled(true);
                } else {
                    throw new SQLException("Error al actualizar la cantidad de productos en inventario");
                }
            } else {
                throw new SQLException("No se pudieron insertar los productos al pedido");
            }

        } catch (Exception e) {
            try {
                if (conexion != null) {
                    conexion.rollback();
                }
                JOptionPane.showMessageDialog(null, "Hay un error al ingresar datos: " + e.getMessage());
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
        } finally {
            try {
                if (ps != null) ps.close();
                if (conexion != null) conexion.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    void calcularTotalP(){
        try{

            double cantidad = Double.parseDouble(CantidadP.getText().trim());
            double precio = Double.parseDouble(PrecioUnitario.getText().trim());
            double totalProducto = cantidad * precio;
            Total_Producto.setText(String.format("%.2f", totalProducto));

        }catch (NumberFormatException ignored){

        }

    }

    void actualizarTotal () {
        double totalV = 0;

        for (Detalle_Pedido producto1 : productosV) {

            try {
                double totalProducto = Double.parseDouble(producto1.TOTAL_PRODUCTO.replace(",", "."));
                totalV += totalProducto;

            } catch (NumberFormatException e) {

                System.out.println("Error al parsear el total del producto" + producto1.TOTAL_PRODUCTO);
            }

        }
        Total.setText(String.format("%.2f", totalV));
        System.out.println("Total del pedido" + totalV);

    }


    void MostrarCatalogo(){
        conectar();
        modTabla.setRowCount(0);
        tableCatalogo.setModel(modTabla);
        try {
            st = conexion.createStatement();
            rs = st.executeQuery("select Id, Nombre, Categoria, Precio, Cantidad_Actual from Producto");
            while (rs.next()) {
                registros[0] = rs.getString("Id");
                registros[1] = rs.getString("Nombre");
                registros[2] = rs.getString("Categoria");
                registros[3] = rs.getString("Precio");
                registros[4] = rs.getString("Cantidad_Actual");
                modTabla.addRow(registros);
            }
        }catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar el catalogo: " + e.getMessage());
        }
    }

    void MostrarPedido(){
        conectar();
        String sqlPEDIDO = "SELECT p.ID_PEDIDO, p.Id, p.FECHA_PEDIDO, p.TOTAL, dp.Id, dp.CANTIDAD_PRODUCTO, dp.DESCRIPCION, dp.PRECIO_UNITARIO, dp.TOTAL_PRODUCTO " +
                "FROM PEDIDO p JOIN DETALLE_PEDIDO dp ON p.ID_PEDIDO = dp.ID_PEDIDO";

        try {
            ps = conexion.prepareStatement(sqlPEDIDO);
            rs = ps.executeQuery();

            DefaultTableModel modelp = (DefaultTableModel) tablePedido.getModel();
            modelp.setRowCount(0);

            if(modelp.getColumnCount()== 0){
                modelp.addColumn("ID_PEDIDO");
                modelp.addColumn("FECHA_PEDIDO");
                modelp.addColumn("TOTAL");
                modelp.addColumn("Id");
                modelp.addColumn("CANTIDAD_PRODUCTO");
                modelp.addColumn("DESCRIPCION");
                modelp.addColumn("PRECIO_UNITARIO");
                modelp.addColumn("TOTAL_PRODUCTO");

                while (rs.next()) {
                    Object[] fila = new Object[8];
                    fila[0] = rs.getString("ID_PEDIDO");
                    fila[1] = rs.getString("FECHA_PEDIDO");
                    fila[2] = rs.getString("TOTAL");
                    fila[3] = rs.getString("Id");
                    fila[4] = rs.getString("CANTIDAD_PRODUCTO");
                    fila[5] = rs.getString("DESCRIPCION");
                    fila[6] = rs.getString("PRECIO_UNITARIO");
                    fila[7] = rs.getString("TOTAL_PRODUCTO");

                    modelp.addRow(fila);
                }

            }
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null,"Error al mostrar los datos" + e.getMessage());
        }

    }

    public void mostrarPedido(){
        Venta pe = new Venta();
        pe.setContentPane(pe.Pedido);
        pe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pe.setVisible(true);
        pe.pack();

    }

    public void regresarCliente (){
        PantallaCliente enlace = new PantallaCliente();
        enlace.mostrarCliente();
    }
}