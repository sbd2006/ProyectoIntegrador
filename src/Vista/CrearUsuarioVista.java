package Vista;

import javax.swing.*;

public class CrearUsuarioVista extends JFrame {
    public JPanel CrearUsuarioVista;
    public JTextField IngresoNombre;
    public JTextField IngresoApellido;
    public JTextField IngresoTelefono;
    public JTextField IngresoDireccion;
    public JTextField IngresoUsuario;
    public JPasswordField IngresoContraseña;
    public JButton BotonCrear;
    public JButton BotonRegresar;
    public JLabel ImageN;

    public CrearUsuarioVista() {
        setTitle("Crear Usuario");
        setContentPane(CrearUsuarioVista);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(1920, 1080);
    }
}