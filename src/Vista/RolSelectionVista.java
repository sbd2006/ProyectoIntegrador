package Vista;

import javax.swing.*;

public class RolSelectionVista extends JFrame {
    public JPanel rolPanel;
    public JTextField idText;
    public JTextField rolText;
    public JTextField nameText;
    public JTable tabla;
    public JButton button1;
    public JButton consultButton;
    public JButton updateButton;
    public JButton deleteButton;

    public RolSelectionVista() {
        setContentPane(rolPanel);
        setTitle("Administrar Roles");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
    }

    public void mostrarVista() {
        setVisible(true);
    }
}
