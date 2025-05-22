package Vista;

import javax.swing.*;

public class VistaCategoria extends JFrame {
    private JTextField Nombre;
    private JButton crearButton;
    private JPanel categoriaPanel;

    public VistaCategoria() {
        setContentPane(categoriaPanel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        pack();

    }

    public JTextField getNombre() {
        return Nombre;
    }

    public JButton getCrearButton() {
        return crearButton;
    }

    public void mostrarVista() {
        setVisible(true);
    }
}
