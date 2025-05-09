package Vista;

import javax.swing.*;

public class RolSelectionVista extends JFrame {
    private JPanel rolPanel;
    private JTextField idText;
    private JComboBox rolText;
    private JTable tabla;
    private JButton button1;
    private JButton consultButton;
    private JButton updateButton;
    private JButton deleteButton;

    public JTextField getIdText() {
        return idText;
    }

    public JComboBox getRolText() {
        return rolText;
    }

    public JTable getTabla() {
        return tabla;
    }

    public JButton getButton1() {
        return button1;
    }

    public JButton getConsultButton() {
        return consultButton;
    }

    public JButton getUpdateButton() {
        return updateButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public RolSelectionVista() {
        setContentPane(rolPanel);
        setTitle("Administrar Roles");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        rolText.addItem("Seleccione un Rol");
        rolText.addItem("Administrador");
        rolText.addItem("Usuario");

    }

    public void mostrarVista() {
        setVisible(true);
    }
}
