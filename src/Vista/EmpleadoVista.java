package Vista;

import javax.swing.*;

public class EmpleadoVista extends JFrame {
    public JPanel PantallaCliente;
    public JButton realizarVentaButton;
    public JButton cerrarSesionButton;
    private JLabel label1;
    private JLabel lblBienvenida;

    public void setNombreUsuario(String nombreUsuario) {
        lblBienvenida.setText("Bienvenido " + nombreUsuario);
    }

    public EmpleadoVista() {
        setTitle("Vista Empleado");
        setContentPane(PantallaCliente);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
    }


    public void mostrarVista() {
        setVisible(true);
    }
}
