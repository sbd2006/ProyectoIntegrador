package Vista;

import Modelo.CrearUsuarioDAO;
import Modelo.CrearUsuario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CrearUsuarioVista extends JFrame {
    public JTextField IngresoNombre;
    public JTextField IngresoApellido;
    public JTextField IngresoTelefono;
    public JTextField IngresoDireccion;
    public JTextField IngresoUsuario;
    public JPasswordField IngresoContraseña;
    public JButton BotonCrear;
    public JButton BotonRegresar;
    public JPanel CrearUsuarioVista;
    private JLabel Imagen;

    private final CrearUsuarioDAO usuarioDAO = new CrearUsuarioDAO();

    public CrearUsuarioVista() {
        setContentPane(CrearUsuarioVista);
        setTitle("Crear Usuario");
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        BotonRegresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                regresarAInicioSesion();
            }
        });

        BotonCrear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crearUsuario();
            }
        });
    }

    public void mostrarVentanaEmergente() {
        setVisible(true);
    }

    public void regresarAInicioSesion() {
        VistaIS vistaIS = new VistaIS();
        vistaIS.setContentPane(vistaIS.Mensaje);
        vistaIS.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        vistaIS.setVisible(true);
        vistaIS.pack();
        dispose();
    }

    private void crearUsuario() {
        String nombre = IngresoNombre.getText();
        String apellido = IngresoApellido.getText();
        String telefono = IngresoTelefono.getText();
        String direccion = IngresoDireccion.getText();
        String usuario = IngresoUsuario.getText();
        String contrasena = new String(IngresoContraseña.getPassword());

        CrearUsuario nuevoUsuario = new CrearUsuario(nombre, apellido, telefono, direccion, usuario, contrasena);

        boolean exito = usuarioDAO.guardarUsuario(nuevoUsuario);
        if (exito) {
            JOptionPane.showMessageDialog(this, "Usuario creado exitosamente");
            regresarAInicioSesion();
        } else {
            JOptionPane.showMessageDialog(this, "Error al crear usuario", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
