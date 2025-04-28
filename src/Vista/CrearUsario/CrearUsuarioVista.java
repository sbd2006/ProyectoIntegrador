package Vista.CrearUsario;

import Vista.VistaIS;

import javax.swing.*;
import java.awt.*;

public class CrearUsuarioVista extends JFrame {

    public JTextField IngresoNombre, IngresoApellido, IngresoTelefono, IngresoDireccion, IngresoUsuario;
    public JPasswordField IngresoContraseña;
    public JButton BotonCrear, BotonRegresar;

    public CrearUsuarioVista (){

        setTitle("Crear Usuario");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelFormulario = new JPanel(new GridLayout(6, 2));
        IngresoNombre = new JTextField();
        IngresoApellido = new JTextField();
        IngresoTelefono = new JTextField();
        IngresoDireccion = new JTextField();
        IngresoUsuario = new JTextField();
        IngresoContraseña = new JPasswordField();

        panelFormulario.add(new JLabel("Nombre"));
        panelFormulario.add(IngresoNombre);
        panelFormulario.add(new JLabel("Apellido"));
        panelFormulario.add(IngresoApellido);
        panelFormulario.add(new JLabel("Teléfono"));
        panelFormulario.add(IngresoTelefono);
        panelFormulario.add(new JLabel("Dirección"));
        panelFormulario.add(IngresoDireccion);
        panelFormulario.add(new JLabel("Usuario"));
        panelFormulario.add(IngresoUsuario);
        panelFormulario.add(new JLabel("Contraseña"));
        panelFormulario.add(IngresoContraseña);

        JPanel panelBotones = new JPanel();
        BotonCrear = new JButton("Crear Usuario");
        BotonRegresar = new JButton("Regresar");
        panelBotones.add(BotonCrear);
        panelBotones.add(BotonRegresar);

        add(panelFormulario, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    public void regresarAInicioSesion() {
        VistaIS vistaIS = new VistaIS();
        vistaIS.setContentPane(vistaIS.Mensaje);
        vistaIS.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        vistaIS.setVisible(true);
        vistaIS.pack();
        dispose();
    }

    public void mostrarVentanaEmergente() {
        setVisible(true);
        pack();
    }
}
