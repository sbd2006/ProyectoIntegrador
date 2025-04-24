package Modelo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Controlador.ControladorIS;

public class InicioSesion extends JFrame {
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
                mostrarCrearUsuario();
                dispose();
            }
        });
    }

    public void mostrarCrearUsuario(){
        CrearUsuario enlace = new CrearUsuario();
        enlace.mostrarVentanaEmergente();
    }

    public void mostrarInicioSesion() {
        InicioSesion is = new InicioSesion();
        is.setContentPane(is.Mensaje);
        is.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        is.setVisible(true);
        is.pack();
        is.setSize(1920, 1080);
    }

    public static void main(String[] args) {
        InicioSesion is = new InicioSesion();
        is.setContentPane(is.Mensaje);
        is.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        is.setVisible(true);
        is.pack();
        is.setSize(1920, 1080);
    }

}