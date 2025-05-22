package Controlador;

import Modelo.RolDAO;
import Vista.AdministradorVista;
import Vista.RolSelectionVista;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;

public class RolSelectionControlador {
    private RolSelectionVista vista;
    private RolDAO modelo;
    private AdministradorVista vistaAdministrador;

    public RolSelectionControlador(RolSelectionVista vista, RolDAO modelo, AdministradorVista vistaAdministrador) {
        this.vista = vista;
        this.modelo = modelo;
        this.vistaAdministrador = vistaAdministrador;

        vista.getConsultButton().addActionListener(e -> consultarUsuarios());
        vista.getUpdateButton().addActionListener(e -> actualizarRol());
        vista.getDeleteButton().addActionListener(e -> eliminarUsuario());
        vista.getButton1().addActionListener(e -> regresar());
        vista.getTabla().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = vista.getTabla().getSelectedRow();
                if (fila != -1) {
                    String id = vista.getTabla().getValueAt(fila, 0).toString();
                    String rol = vista.getTabla().getValueAt(fila, 3).toString();

                    vista.getIdText().setText(id);
                    vista.getRolText().setSelectedItem(rol);
                }
            }
        });


        vista.mostrarVista();
    }

    private void consultarUsuarios() {
        try {
            List<String[]> usuarios = modelo.consultarUsuarios();
            DefaultTableModel tabla = new DefaultTableModel(new String[]{"ID", "Nombre", "Apellido", "Rol", "Usuario"}, 0);
            for (String[] fila : usuarios) {
                tabla.addRow(fila);
            }
            vista.getTabla().setModel(tabla);
        } catch (SQLException e) {
            mostrarError("Error al consultar: " + e.getMessage());
        }
    }

    private void actualizarRol() {
        try {
            String idText = vista.getIdText().getText().trim();

            if (idText.isEmpty()) {
                mostrarError("El campo ID no puede estar vacío.");
            }else {
                int id = Integer.parseInt(idText);
                String rol = vista.getRolText().getSelectedItem().toString();

                if (modelo.actualizarRol(id, rol)) {
                    JOptionPane.showMessageDialog(null , "Se actualizo correctamente");
                    limpiarCampos();
                    consultarUsuarios();
                }
            }

        } catch (Exception e) {
            mostrarError("Error al actualizar: " + e.getMessage());
        }
    }

    private void eliminarUsuario() {
        try {
            String idText = vista.getIdText().getText().trim();
            if (idText.isEmpty()) {
                mostrarError("El campo ID no puede estar vacío.");
            }else {
                int id = Integer.parseInt(vista.getIdText().getText());
                if (modelo.eliminarUsuario(id)) {
                    JOptionPane.showMessageDialog(null , "Se elimino correctamente");
                    limpiarCampos();
                    consultarUsuarios();
                }
            }
        } catch (Exception e) {
            mostrarError("Error al eliminar: " + e.getMessage());
        }
    }

    private void limpiarCampos() {
        vista.getIdText().setText("");
        vista.getRolText().setSelectedIndex(0);
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(vista, mensaje);
    }

    public void iniciarVista() {
        vista.mostrarVista();
    }

    private void regresar(){
        vista.dispose();
        vistaAdministrador.setVisible(true);
    }

}