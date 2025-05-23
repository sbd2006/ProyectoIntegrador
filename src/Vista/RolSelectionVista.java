package Vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class RolSelectionVista extends JFrame {
    private JPanel rolPanel;
    private JTextField idText;
    private JComboBox rolText;
    private JTable tabla;
    private JButton button1;
    private JButton consultButton;
    private JButton updateButton;
    private JButton deleteButton;

    // Agregado: modelo de tabla
    private DefaultTableModel modTabla;

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

    // Getter para el modelo de tabla
    public DefaultTableModel getModTabla() {
        return modTabla;
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

        // Configurar el modelo de tabla con celdas no editables
        modTabla = new DefaultTableModel(new String[]{"ID", "Rol", "Descripción"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Asignar el modelo a la tabla
        tabla.setModel(modTabla);
    }

    public void mostrarVista() {
        setVisible(true);
    }
}
