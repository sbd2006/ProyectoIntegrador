package Controlador;

import Modelo.RolDAO;
import Vista.RolSelectionVista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class RolSelectionControlador {
    private RolSelectionVista vista;
    private RolDAO modelo;

    public RolSelectionControlador(RolSelectionVista vista, RolDAO modelo) {
        this.vista = vista;
        this.modelo = modelo;

        vista.consultButton.addActionListener(e -> consultarUsuarios());
        vista.updateButton.addActionListener(e -> actualizarRol());
        vista.deleteButton.addActionListener(e -> eliminarUsuario());
        vista.button1.addActionListener(e -> vista.dispose());

        vista.mostrarVista();
    }

    private void consultarUsuarios() {
        try {
            List<String[]> usuarios = modelo.consultarUsuarios();
            DefaultTableModel tabla = new DefaultTableModel(new String[]{"ID", "Nombre", "Apellido", "Rol", "Usuario"}, 0);
            for (String[] fila : usuarios) {
                tabla.addRow(fila);
            }
            vista.tabla.setModel(tabla);
        } catch (SQLException e) {
            mostrarError("Error al consultar: " + e.getMessage());
        }
    }

    private void actualizarRol() {
        try {
            int id = Integer.parseInt(vista.idText.getText());
            String rol = vista.rolText.getText();
            if (modelo.actualizarRol(id, rol)) {
                limpiarCampos();
                consultarUsuarios();
            }
        } catch (Exception e) {
            mostrarError("Error al actualizar: " + e.getMessage());
        }
    }

    private void eliminarUsuario() {
        try {
            int id = Integer.parseInt(vista.idText.getText());
            if (modelo.eliminarUsuario(id)) {
                limpiarCampos();
                consultarUsuarios();
            }
        } catch (Exception e) {
            mostrarError("Error al eliminar: " + e.getMessage());
        }
    }

    private void limpiarCampos() {
        vista.idText.setText("");
        vista.rolText.setText("");
        vista.nameText.setText("");
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(vista, mensaje);
    }
}