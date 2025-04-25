package Vista;

import javax.swing.*;

public class AdministradorVista extends JFrame {
    public JPanel administracion;
    public JButton productoButton;
    public JButton cerrarSesionButton;
    public JButton gestionDeUsuariosButton;

    public AdministradorVista() {
        setTitle("Vista Administrador");
        setContentPane(administracion);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
    }

    public void mostrarVista() {
        setVisible(true);
    }
}
