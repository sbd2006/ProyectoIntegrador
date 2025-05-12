    package Vista;

    import Controlador.ControladorIS;
    import Controlador.VentaControlador;
    import Modelo.ModeloIS;
    import Modelo.VentaDAO;

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

        public void cerrarSesion() {
            dispose();
            VistaIS nuevaVistaLogin = new VistaIS();
            ModeloIS nuevoModeloLogin = new ModeloIS(nuevaVistaLogin);
            ControladorIS nuevoControladorLogin = new ControladorIS(nuevoModeloLogin, nuevaVistaLogin);
            nuevaVistaLogin.setControlador(nuevoControladorLogin);
            nuevaVistaLogin.setVisible(true);
        }

        public void mostrarEmpleado() {
            setVisible(true);
        }

        public void mostrarVenta() {
            VentaVista vistaV = new VentaVista();
            VentaDAO dao = new VentaDAO();
            VentaControlador controlador = new VentaControlador(vistaV, dao, this, idEmpleadoActual);
            vistaV.setVisible(true);
            this.setVisible(false);
        }

    }