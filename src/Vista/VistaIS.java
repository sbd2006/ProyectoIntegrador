package Vista;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Controlador.ControladorIS;
import Modelo.CrearUsuario;

public class VistaIS extends JFrame {
    public JPanel Mensaje;
    private JLabel MensajeBienvenida;
    private JLabel MensajeLog;
    private JLabel MensajeUser;
    private JTextField IngresoUser;
    private JPasswordField IngresoPassword;
    private JLabel MensajePassword;
    private JButton Login;
    private JButton CreateUser;

    public JTextField getIngresoUser() {
        return IngresoUser;
    }

    public JPasswordField getIngresoPassword() {
        return IngresoPassword;
    }

    public void setControlador(ControladorIS controlador) {
        Login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlador.Validacion();
            }
        });

        CreateUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlador.mostrarCrearUsuario();
                dispose();
            }
        });
    }

    public void mostrarInicioSesion() {
        VistaIS is = new VistaIS();
        is.setContentPane(is.Mensaje);
        is.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        is.setVisible(true);
        is.pack();
        is.setSize(1920, 1080);
    }

    public void mostrarVista() {
        this.setVisible(true);
    }


}