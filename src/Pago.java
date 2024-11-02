import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Pago {
    private JPanel panel1;
    private JRadioButton efectivoRadioButton;
    private JRadioButton tarjetaDeCreditoDebitoRadioButton;
    private JRadioButton transferenciaRadioButton;
    private JButton regresarButton;
    private JButton pagarButton;

    public Pago() {
        // Configuración del JFrame
        JFrame frame = new JFrame("Pago");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        regresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Redirigir a la pantalla de Pedido
                new Pedido();
                frame.dispose(); // Cerrar la ventana actual
            }
        });

        pagarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Aquí puedes implementar la lógica para procesar el pago
                JOptionPane.showMessageDialog(frame, "Pago procesado correctamente!");
                frame.dispose(); // Cerrar la ventana actual
            }
        });
    }

    public static void main(String[] args) {
        new Pago();
    }
}



