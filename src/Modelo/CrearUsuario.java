package Modelo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CrearUsuario extends JFrame {
    public JPanel CrearUsuario;
    private JTextField IngresoNombre;
    private JTextField IngresoApellido;
    private JTextField IngresoTelefono;
    private JTextField IngresoDireccion;
    private JTextField IngresoUsuario;
    private JPasswordField IngresoContraseña;
    private JButton BotonCrear;
    private JButton BotonRegresar;
    Connection conexion;

    public CrearUsuario() {
        setContentPane(CrearUsuario);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);


        BotonCrear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertarUsuario();
                regresarAInicioSesion();
            }
        });


        BotonRegresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                regresarAInicioSesion();
                dispose();
            }
        });
    }


    void conectar() {
        try {
            conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/PostresMariaJose", "root", "Santi104");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void insertarUsuario() {
        conectar();
        String nombre = IngresoNombre.getText();
        String apellido = IngresoApellido.getText();
        String telefono = IngresoTelefono.getText();
        String direccion = IngresoDireccion.getText();
        String usuario = IngresoUsuario.getText();
        String password = new String(IngresoContraseña.getPassword());


        String query = "INSERT INTO usuarios (Nombre, Apellido, Telefono, Direccion, Usuario.Usuario, Pass) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setString(1, nombre);
            ps.setString(2, apellido);
            ps.setString(3, telefono);
            ps.setString(4, direccion);
            ps.setString(5, usuario);
            ps.setString(6, password);

            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Usuario.Usuario creado exitosamente");

            regresarAInicioSesion();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al crear usuario: " + e.getMessage());
        } finally {
            try {
                if (conexion != null && !conexion.isClosed()) {
                    conexion.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }


    public void regresarAInicioSesion() {
        InicioSesion inicioSesion = new InicioSesion();
        inicioSesion.setContentPane(inicioSesion.Mensaje);
        inicioSesion.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        inicioSesion.setVisible(true);
        inicioSesion.pack();
        dispose();
    }

    public void mostrarVentanaEmergente() {
        setVisible(true);
        pack();
    }
}