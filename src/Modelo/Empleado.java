package Modelo;

import Vista.VistaIS;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Empleado extends JFrame {
    public JPanel PantallaCliente;
    private JLabel label1;
    private JButton realizarVentaButton;
    private JButton cerrarSesionButton;


    public Empleado() {

        realizarVentaButton.addActionListener(new ActionListener() {
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

    public void mostrarEmpleado(){
        Empleado pc = new Empleado();
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
        VistaIS enlace = new VistaIS();
        enlace.mostrarInicioSesion();

    }

}