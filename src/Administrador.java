import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Administrador extends JFrame {
    private JPanel administracion;
    private JButton productoButton;
    private JButton cerrarSesionButton;

    public Administrador(){
        productoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    enlazarProducto();
                }finally {

                }

            }
        });
        cerrarSesionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cerrarSesionAdmin();
            }
        });
    }
    public void enlazarProducto(){
        Producto enlace = new Producto();
        enlace.mostrarProducto();

    }



    public void mostrarAdministrador(){
        Administrador admin = new Administrador();
        admin.setContentPane(new Administrador().administracion);
        admin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        admin.setVisible(true);
        admin.pack();

    }

    public void cerrarSesionAdmin(){
    InicioSesion enlace = new InicioSesion();
    enlace.mostrarInicioSesion();

    }



}