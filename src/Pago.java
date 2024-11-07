import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Pago extends JFrame{

    private JRadioButton efectivoRadioButton;
    private JRadioButton tarjetaDeCreditoDebitoRadioButton;
    private JRadioButton transferenciaRadioButton;
    private JButton regresarButton;
    private JButton pagarButton;
    private JPanel panel1;
    private ButtonGroup group;

    public Pago() {

        ButtonGroup group = new ButtonGroup();
        group.add(efectivoRadioButton);
        group.add(tarjetaDeCreditoDebitoRadioButton);
        group.add(transferenciaRadioButton);


        pagarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Verificar si algún JRadioButton está seleccionado
                if (efectivoRadioButton.isSelected() || tarjetaDeCreditoDebitoRadioButton.isSelected() || transferenciaRadioButton.isSelected()) {
                    JOptionPane.showMessageDialog(null, "¡Su pedido ha sido exitoso!");

                }else{
                    JOptionPane.showMessageDialog(null, "¡Por favor seleccione un metodo de pago!");
                }

                // Si no hay ningún botón seleccionado, no hace nada
            }
        });

        regresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{

                    back();



                }finally {

                }
            }
        });

    }

    private void mostrarMensajeExito() {
        JOptionPane.showMessageDialog(null, "¡Su pedido ha sido exitoso!", null, JOptionPane.INFORMATION_MESSAGE);
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