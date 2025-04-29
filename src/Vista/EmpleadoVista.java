package Vista;

import javax.swing.*;

public class EmpleadoVista extends JFrame {
    public JPanel PantallaCliente;
    public JButton realizarVentaButton;
    public JButton cerrarSesionButton;
    private JLabel lblBienvenida;

    public EmpleadoVista() {
        setTitle("Vista Empleado");
        setContentPane(PantallaCliente);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
    }

    public void setNombreUsuario(String nombreUsuario) {
        lblBienvenida.setText("Bienvenido " + nombreUsuario);
    }

    public void mostrarEmpleado() {
        setVisible(true);
    }
}