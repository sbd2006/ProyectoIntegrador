package Controlador;

import Modelo.ModeloMovimiento;
import Modelo.MovimientoDAO;
import Vista.AdministradorVista;
import Vista.MovimientoVista;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class ControladorMovimiento {
    private MovimientoVista vista;
    private AdministradorVista adminVista;

    public ControladorMovimiento(MovimientoVista vista, AdministradorVista adminVista) {
        this.vista = vista;
        this.adminVista = adminVista;

        this.vista.getRegistrarButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarMovimiento();
            }
        });

        this.vista.getRegresar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vista.dispose();
                adminVista.setVisible(true);
            }
        });

    }

    private void registrarMovimiento() {
        try {
            if (vista.getProductoId().getText().isEmpty() ||
                    vista.getCantidad().getText().isEmpty() ||
                    vista.getObservacion().getText().isEmpty()) {

                JOptionPane.showMessageDialog(null,
                        "Por favor completa todos los campos antes de registrar el movimiento.",
                        "Campos vacíos", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int productoId = Integer.parseInt(vista.getProductoId().getText());
            int tipoMovimientoId = vista.getTipoMovimientoSeleccionado();
            int documentoId = vista.getDocumentoId();
            int estadoMovimientoId = 1; // Aplicado
            int cantidad = Integer.parseInt(vista.getCantidad().getText());
            String fecha = LocalDate.now().toString();
            String observacion = vista.getObservacion().getText();

            ModeloMovimiento movimiento = new ModeloMovimiento(
                    productoId, tipoMovimientoId, documentoId,
                    estadoMovimientoId, cantidad, fecha, observacion);

            new MovimientoDAO().registrarMovimiento(movimiento);

            JOptionPane.showMessageDialog(null,
                    "Movimiento registrado exitosamente.",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null,
                    "Asegúrate de ingresar solo números en los campos numéricos.",
                    "Error de formato", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                    "Ocurrió un error al registrar el movimiento: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}