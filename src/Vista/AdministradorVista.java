package Vista;


import Controlador.*;
import Modelo.ModeloIS;
import Modelo.ModeloP;
import Modelo.RolDAO;
import Modelo.VentaDAO;
import Modelo.Conexion;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class AdministradorVista extends JFrame {
    public JPanel administracion;
    public JButton productoButton;
    public JButton cerrarSesionButton;
    public JButton gestionDeUsuariosButton;
    private JLabel labelBienvenida;
    private JButton consultarVentasButton;
    private JButton ReporteVentaButton;
    private JButton inventarioButton;
    private MovimientoVista movimientoVista;
    private ControladorMovimiento controladorMovimiento;

    public AdministradorVista() {
        setTitle("Vista Administrador");
        setContentPane(administracion);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        // Aquí se conectan los botones con sus acciones
        productoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirVistaProducto();
            }
        });

        cerrarSesionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cerrarSesion();
            }
        });

        gestionDeUsuariosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirGestionUsuarios();
            }
        });
        consultarVentasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirAdministracionVentas();
            }
        });

        inventarioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (movimientoVista == null || controladorMovimiento == null) {
                    movimientoVista = new MovimientoVista();
                    controladorMovimiento = new ControladorMovimiento(movimientoVista, AdministradorVista.this);
                }
                dispose();
                controladorMovimiento.cargarTablas();
                movimientoVista.setVisible(true);
            }
        });

    }

    public void setNombreUsuario(String nombreUsuario) {
        labelBienvenida.setText("¡Bienvenido Administrador " + nombreUsuario + "¡");
    }

    public JButton getReporteVentaButton() {
        return ReporteVentaButton;
    }


    // Métodos de acción

    private void abrirVistaProducto() {
        VistaP productoVista = new VistaP();
        ModeloP productoModelo = new ModeloP();
        ControladorP productoControlador = new ControladorP(productoModelo, productoVista, this);
        productoControlador.iniciarVista();
        this.setVisible(false);
    }

    private void cerrarSesion() {
        dispose();

        VistaIS nuevaVistaLogin = new VistaIS();
        ModeloIS nuevoModeloLogin = new ModeloIS(nuevaVistaLogin);
        ControladorIS nuevoControladorLogin = new ControladorIS(nuevoModeloLogin, nuevaVistaLogin);
        nuevaVistaLogin.setControlador(nuevoControladorLogin);
        nuevaVistaLogin.setVisible(true);
    }



    private void abrirGestionUsuarios() {
        try {
            RolSelectionVista rolVista = new RolSelectionVista();
            RolDAO rolDAO = new RolDAO();
            RolSelectionControlador rolControlador = new RolSelectionControlador(rolVista, rolDAO,this);
            rolControlador.iniciarVista();
            this.setVisible(false);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Error de base de datos: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

    }
    private void abrirAdministracionVentas() {
        try {
            Conexion connection = new Conexion();
            connection.conectar();

            VentaDAO modeloVentas = new VentaDAO();

            // Se pasa la instancia actual (this) al constructor de la vista
            AdministracionVentasVista vistaVentas = new AdministracionVentasVista(this);

            AdministracionVentasControlador controladorVentas =
                    new AdministracionVentasControlador(vistaVentas, modeloVentas);

            controladorVentas.iniciarVista();
            this.setVisible(false);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al abrir la vista de administración de ventas: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirVistaMovimiento() {
        MovimientoVista vistaMovimiento = new MovimientoVista(); // tu clase de interfaz
        ControladorMovimiento controladorMovimiento = new ControladorMovimiento(vistaMovimiento, this); // controlador que maneja eventos
        vistaMovimiento.mostrarVista(); // se abre la ventana
        this.setVisible(false); // oculta la actual
}


    public void regresar() {

        setVisible(true);
    }
}