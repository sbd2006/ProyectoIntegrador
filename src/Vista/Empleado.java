package Vista;

import Vista.VistaIS;
import Modelo.Venta;
import Modelo.ModeloP;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.time.LocalDate;
import java.sql.DriverManager;


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

    public void enlazarVenta() {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/?user=root", "root", "OH{c<6H1#cQ%F69$i");
            Venta model = new Venta(1, LocalDate.now());
            VentaVista view = new VentaVista();
            VentaDAO dao = new VentaDAO(con);
            ModeloP modelo = new ModeloP();
            VentaControlador controller = new VentaControlador(view, modelo);
            view.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void cerrarSesionCliente (){
        VistaIS enlace = new VistaIS();
        enlace.mostrarInicioSesion();

    }

}