package Vista;

import Controlador.ControladorP;
import Modelo.ModeloP;
import Modelo.Rolselection;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Administrador extends JFrame {
    private JPanel administracion;
    private JButton productoButton;
    private JButton cerrarSesionButton;
    private JButton gestionDeUsuariosButton;

    public Administrador(){
        productoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    enlazarProducto();
                    dispose();
                }finally {

                }

            }
        });
        cerrarSesionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cerrarSesionAdmin();
                dispose();
            }
        });
        gestionDeUsuariosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Rolselection enlace = new Rolselection();
                enlace.mostrarRolSelection();
            }
        });
    }
    public void enlazarProducto(){
        ModeloP modelo = new ModeloP();
        VistaP vista = new VistaP();
        ControladorP controlador = new ControladorP(modelo, vista);
    }



    public void mostrarAdministrador(){
        Administrador admin = new Administrador();
        admin.setContentPane(admin.administracion);
        admin.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        admin.setVisible(true);
        admin.pack();

    }

    public void cerrarSesionAdmin(){
    VistaIS enlace = new VistaIS();
    enlace.mostrarInicioSesion();

    }

}