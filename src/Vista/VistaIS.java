package Vista;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Controlador.ControladorIS;

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

    public VistaIS() {
        setTitle("Inicio de Sesión");
        setContentPane(Mensaje);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

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

    public void mostrarVista() {
        this.setVisible(true);
        dispose();
    }
}