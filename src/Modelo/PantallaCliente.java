package Modelo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PantallaCliente extends JFrame {
    public JPanel PantallaCliente;
    private JLabel label1;
    private JButton realizarPedidoButton;
    private JButton cerrarSesionButton;


    public PantallaCliente () {

        realizarPedidoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    enlazarVenta();
                    dispose();
                } finally {

                }
            }
        });
        cerrarSesionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cerrarSesionCliente();
                dispose();
            }
        });
    }

    public void mostrarCliente(){
        PantallaCliente pc = new PantallaCliente();
        pc.setContentPane(pc.PantallaCliente);
        pc.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pc.setVisible(true);
        pc.pack();
    }

    public void enlazarVenta(){
        Venta enlace1 = new Venta();
        enlace1.mostrarPedido();
    }

    public void cerrarSesionCliente (){
        InicioSesion enlace = new InicioSesion();
        enlace.mostrarInicioSesion();

    }



}

