package Modelo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Producto extends JFrame {
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


    Connection conexion;
    PreparedStatement ps;
    String[] campos = {"Id","Nombre","Categoria","Precio","Cantidad_Actual"};
    String[] registros = new String[10];
    DefaultTableModel modTabla = new DefaultTableModel(null,campos);
    Statement st;
    ResultSet rs;




    public Producto() {


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
        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Eliminar();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        editarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Editar();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });

        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    Guardar();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        regresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            try {
                Regresar();
                dispose();
            }finally {

            }
            }
        });
    }

    void conectar(){
        try{
            conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/PostresMariaJose","root","Santi104");
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    void Mostrar() throws SQLException{
        conectar();
        modTabla.setRowCount(0);
        table1.setModel(modTabla);
        st = conexion.createStatement();
        rs= st.executeQuery("select Id, Nombre, Categoria, Precio, Cantidad_Actual from Producto");
        while (rs.next()){
            registros[0]= rs.getString("Id");
            registros[1]= rs.getString("Nombre");
            registros[2]= rs.getString("Categoria");
            registros[3]= rs.getString("Precio");
            registros[4]= rs.getString("Cantidad_Actual");
            modTabla.addRow(registros);
        }
    }

    void Guardar() throws SQLException{
        conectar();
        ps = conexion.prepareStatement("insert into Producto(Id, Nombre, Categoria, Precio, Cantidad_Actual) values (?,?,?,?,?)");
        ps.setInt(1,Integer.parseInt(Idtext.getText()));
        ps.setString(2,nombretext.getText());
        ps.setString(3,categoriatext.getText());
        ps.setInt(4, Integer.parseInt(preciotext.getText()));
        ps.setInt(5, Integer.parseInt(cantidadtext.getText()));
        if(ps.executeUpdate()>0) {

            nombretext.setText("");
            categoriatext.setText("");
            preciotext.setText("");
            cantidadtext.setText("");
            Mostrar();
        }

    }

    void Editar() throws SQLException{
        conectar();
        ps = conexion.prepareStatement("update Producto set Precio=?, Cantidad_Actual=? where Id=?");
        ps.setInt(1, Integer.parseInt(preciotext.getText()));
        ps.setInt(2, Integer.parseInt(cantidadtext.getText()));
        ps.setInt(3, Integer.parseInt(Idtext.getText()));
        if(ps.executeUpdate()>0) {

            nombretext.setText("");
            categoriatext.setText("");
            preciotext.setText("");
            cantidadtext.setText("");
            Mostrar();
        }

    }

    void Eliminar() throws SQLException{
        conectar();
        ps = conexion.prepareStatement("delete from Producto where Id =?");
        ps.setInt(1, Integer.parseInt(Idtext.getText()));

        if(ps.executeUpdate()>0) {

            nombretext.setText("");
            categoriatext.setText("");
            preciotext.setText("");
            cantidadtext.setText("");
            Mostrar();
        }

    }

    public void Regresar() {
        Administrador enlace = new Administrador();
        enlace.mostrarAdministrador();

    }

    public void mostrarProducto() {
        Producto Pr = new Producto();
        Pr.setContentPane(Pr.producto);
        Pr.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Pr.setVisible(true);
        Pr.pack();
    }



}