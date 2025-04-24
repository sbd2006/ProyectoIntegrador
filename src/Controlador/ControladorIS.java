package Controlador;

import Modelo.Administrador;
import Modelo.CrearUsuario;
import Modelo.InicioSesion;
import Modelo.Empleado;
import Modelo.Conexion;

import javax.swing.*;
import java.sql.*;

public class ControladorIS {
    private InicioSesion modelo;
    private ResultSet rs;
    private Statement st;
    private PreparedStatement ps;

    public ControladorIS(InicioSesion modelo) {
        this.modelo = modelo;
    }

    public JTextField getUser() {
        return modelo.getIngresoUser();
    }

    public JPasswordField getPass() {
        return modelo.getIngresoPassword();
    }

    public void Validacion() {
        Conexion conX = new Conexion();
        conX.conectar();

        String usuario = getUser().getText();
        String pass = String.valueOf(getPass().getPassword());

        try {
            st = conX.getConexion().createStatement();
            rs = st.executeQuery("SELECT * FROM usuarios WHERE Usuario = '" + usuario + "' AND Pass = '" + pass + "'");

            if (rs.next()) {
                String userType = rs.getString("tipo");

                if ("Administrador".equals(userType)) {
                    JOptionPane.showMessageDialog(null, "Bienvenido Administrador");
                    Administrador enlace = new Administrador();
                    enlace.mostrarAdministrador();
                    modelo.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Bienvenido Cliente");
                    Empleado enlace = new Empleado();
                    enlace.mostrarCliente();
                    modelo.dispose();
                }

            } else {
                JOptionPane.showMessageDialog(null, "Las credenciales no son correctas");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    public void mostrarCrearUsuario(){
        CrearUsuario enlace = new CrearUsuario();
        enlace.mostrarVentanaEmergente();
    }
}
