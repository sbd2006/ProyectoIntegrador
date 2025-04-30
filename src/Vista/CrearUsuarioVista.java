package Vista;


import Controlador.ControladorIS;
import Modelo.ModeloIS;
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

    public void regresarAInicioSesion() {
        dispose();

        VistaIS nuevaVistaLogin = new VistaIS();
        ModeloIS nuevoModeloLogin = new ModeloIS(nuevaVistaLogin);
        ControladorIS nuevoControladorLogin = new ControladorIS(nuevoModeloLogin, nuevaVistaLogin);
        nuevaVistaLogin.setControlador(nuevoControladorLogin);
        nuevaVistaLogin.setVisible(true);
    }
}