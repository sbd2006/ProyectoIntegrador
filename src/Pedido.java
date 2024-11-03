import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Pedido extends JFrame{
    private JTable table1;
    private JTextField textField4;
    private JPanel Pedido;
    private JTextField textField2;
    private JTextField textField3;
    private JButton confirmarPedidoButton;
    private JButton regresarButton;
    private JTextField textField1;
    private JButton mostrarButton;


    Connection conexion;
    PreparedStatement ps;
    String[] campoP = {"id_pedido", "producto", "tamaño", "precio"};
    String[] registros = new String[10];
    DefaultTableModel modTabla = new DefaultTableModel(null, campoP);
    Statement st;
    ResultSet rs;

    public Pedido() {
        // Inicializa la tabla y establece el modelo
        table1.setModel(modTabla);
        textField4.setEditable(false);
        textField1.setEditable(false);



        // Agrega un DocumentListener al campo de cantidad para detectar cambios en tiempo real
        textField2.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { actualizarTotal(); }
            @Override
            public void removeUpdate(DocumentEvent e) { actualizarTotal(); }
            @Override
            public void changedUpdate(DocumentEvent e) { actualizarTotal(); }
        });

        // Agrega un ListSelectionListener a la tabla para detectar cambios en la selección
        table1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) { actualizarTotal(); }
        });
        mostrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Mostrar();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        regresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            try {
                Behind();

            }finally {

            }
            }
        });
        confirmarPedidoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            try{


                if (textField4.getText().trim().isEmpty()||textField4.getText().trim().equals("0") ) {  // Cambiamos la condición a "equals('0')"
                    JOptionPane.showMessageDialog(null, "Por favor seleccione un producto");
                } else {

                    Pago enlace = new Pago();
                    enlace.pago();
                    mostraPago();
                }


            }catch (NumberFormatException ex){

            JOptionPane.showMessageDialog(null, "Error, el valor total debe ser numerico");
            }

            }
        });
        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table1.getSelectedRow();
                if (row != -1) {
                    textField1.setText(table1.getValueAt(row, 1).toString());

                }
            }
        });
    }

    void conectar() {
        try {
            conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/PostresMariaJose", "root", "OH{c<6H1#cQ%F69$i");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    void Mostrar() throws SQLException {
        conectar();
        modTabla.setRowCount(0);
        st = conexion.createStatement();
        rs = st.executeQuery("select id_pedido, producto, tamaño, precio from Pedido");
        while (rs.next()) {
            registros[0] = rs.getString("id_pedido");
            registros[1] = rs.getString("producto");
            registros[2] = rs.getString("tamaño");
            registros[3] = rs.getString("precio");
            modTabla.addRow(registros);
        }
        table1.setModel(modTabla);
    }




    // Método para actualizar el campo total al cambiar la cantidad o la selección en la tabla
    private void actualizarTotal() {
        try {
            // Obtiene el valor de cantidad ingresado por el usuario
            int cantidad = Integer.parseInt(textField2.getText());

            // Obtiene la fila seleccionada en la tabla
            int filaSeleccionada = table1.getSelectedRow();
            if (filaSeleccionada != -1) {
                // Obtiene el precio del producto en la columna correspondiente (ajusta si es necesario)
                double precio = Double.parseDouble(table1.getValueAt(filaSeleccionada, 3).toString());
                // Calcula el total
                double total = cantidad * precio;
                // Muestra el total en el campo de texto "totaltext"
                textField4.setText(String.valueOf(total));
            }
        } catch (NumberFormatException e) {
            // Si la entrada no es válida, muestra 0 en el campo de total
            textField4.setText("0");
        }
    }


    public void mostrarPedido(){
        Pedido pe = new Pedido();
        pe.setContentPane(pe.Pedido); // Uso de la misma instancia 'pe'
        pe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pe.setVisible(true);
        pe.pack();
    }

    public void Behind() {
        PantallaCliente enlace = new PantallaCliente();
        enlace.mostrarCliente();

    }

    public void mostraPago(){
        Pago enlace = new Pago();
        enlace.pago();

    }


}
