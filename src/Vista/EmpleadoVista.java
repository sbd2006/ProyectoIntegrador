package Vista;

import javax.swing.*;

public class EmpleadoVista extends JFrame {
    public JPanel PantallaCliente;
    public JButton realizarVentaButton;
    public JButton cerrarSesionButton;
    private JLabel Bienvenido;
    private JButton ReporteVentaButton;
    private JLabel lblBienvenida;

    public EmpleadoVista() {
        setTitle("Vista Empleado");
        setContentPane(PantallaCliente);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();


    }
    public JButton getReporteVentaButton() {
        return ReporteVentaButton;
    }


    public void setNombreUsuario(String nombreUsuario) {
        Bienvenido.setText("Bienvenido Emplead@ " + nombreUsuario);
    }

    public void mostrarEmpleado() {
        setVisible(true);
    }
}