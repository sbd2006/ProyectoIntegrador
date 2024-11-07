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
                    enlazarPedido();
                } finally {

                }
            }
        });
        cerrarSesionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cerrarSesionCliente();
            }
        });
    }

    public void mostrarCliente(){
        PantallaCliente pc = new PantallaCliente();
        pc.setContentPane(new PantallaCliente().PantallaCliente);
        pc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pc.setVisible(true);
        pc.pack();
    }

    public void enlazarPedido(){
        Pedido enlace1 = new Pedido();
        enlace1.mostrarPedido();
    }

    public void cerrarSesionCliente (){
        InicioSesion enlace = new InicioSesion();
        enlace.mostrarInicioSesion();

    }



}

