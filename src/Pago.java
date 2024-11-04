import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;

public class Pago extends JFrame {
    private JPanel panel1;
    private JRadioButton efectivoRadioButton;
    private JRadioButton tarjetaDeCreditoDebitoRadioButton;
    private JRadioButton transferenciaRadioButton;
    private JButton regresarButton;
    private JButton pagarButton;
    private ButtonGroup group;



    public Pago(){
        efectivoRadioButton = new JRadioButton("Efectivo");
        tarjetaDeCreditoDebitoRadioButton= new JRadioButton("Tarjeta de credito/debito");
        transferenciaRadioButton = new JRadioButton("Transferencia");



        group = new ButtonGroup();
        group.add(efectivoRadioButton);
        group.add(tarjetaDeCreditoDebitoRadioButton);
        group.add(transferenciaRadioButton);



        regresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{

                    back();



                }finally {

                }
            }
        });
        pagarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    mostrarMensajeExito();

                }finally {

                }
            }
        });
    }

    private void mostrarMensajeExito() {
        JOptionPane.showMessageDialog(null, "¡Su pedido ha sido exitoso!", null, JOptionPane.INFORMATION_MESSAGE);
    }


    void Validacionpago(){



    }

    public void back(){
        Pedido enlace = new Pedido();
        enlace.mostrarPedido();

    }
    public void pago(){
        Pago pg = new Pago();
        pg.setContentPane(new Pago().panel1);
        pg.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pg.setVisible(true);
        pg.pack();
    }

}