    package Vista;
    import javax.swing.*;

    public class EmpleadoVista extends JFrame {
        public JPanel PantallaCliente;
        public JButton realizarVentaButton;
        public JButton cerrarSesionButton;
        private JLabel Bienvenido;
        private JButton ReporteVentaButton;
        private int idEmpleadoActual;

        public EmpleadoVista(int idEmpleadoActual) {
            this.idEmpleadoActual = idEmpleadoActual;
            setTitle("Vista Empleado");
            setContentPane(PantallaCliente);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            pack();
            setExtendedState(JFrame.MAXIMIZED_BOTH);
            setLocationRelativeTo(null);
        }

        public JButton getReporteVentaButton() {
            return ReporteVentaButton;
        }

        public void setNombreUsuario(String nombreUsuario) {
            Bienvenido.setText("Bienvenido Emplead@ " + nombreUsuario);
        }
    }