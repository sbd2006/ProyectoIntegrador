package Vista;

import Controlador.ControladorP;
import Modelo.ModeloP;
import Modelo.RolDAO;
import Controlador.RolSelectionControlador;
import Controlador.AdministracionVentasControlador;
import Vista.AdministracionVentasVista;

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

    public AdministradorVista() {
        setTitle("Vista Administrador");
        setContentPane(administracion);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();

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

            }
        });
    }

    public void setNombreUsuario(String nombreUsuario) {
        labelBienvenida.setText("¡Bienvenido Administrador " + nombreUsuario + "¡");
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
        VistaIS inicio = new VistaIS();
        inicio.mostrarInicioSesion();
        dispose();
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
    public void regresar() {
        setVisible(true);
    }
}