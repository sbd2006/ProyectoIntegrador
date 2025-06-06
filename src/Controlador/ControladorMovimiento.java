package Controlador;

import Modelo.ModeloMovimiento;
import Modelo.ModeloP;
import Modelo.MovimientoDAO;
import Vista.AdministradorVista;
import Vista.MovimientoVista;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ControladorMovimiento {
    private MovimientoVista vista;
    private AdministradorVista adminVista;
    private Map<String, Integer> mapaProductos;
    private ModeloP modeloP;

    public ControladorMovimiento(MovimientoVista vista, AdministradorVista adminVista) {
        this.vista = vista;
        this.adminVista = adminVista;
        this.modeloP = new ModeloP();
        this.mapaProductos = modeloP.obtenerProductosNombreId();
        cargarTiposDocumentoEnCombo();

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

        vista.setComboBoxItems(new ArrayList<>(mapaProductos.keySet()));


    }

    private void registrarMovimiento() {
        try {
            if (vista.getComboProductos().getSelectedItem() == null ||
                    vista.getCantidad().getText().isEmpty() ||
                    vista.getObs().getText().isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "Por favor completa todos los campos antes de registrar el movimiento.",
                        "Campos vacíos", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String nombreSeleccionado = (String) vista.getComboProductos().getSelectedItem();
            int productoId = mapaProductos.get(nombreSeleccionado);
            String tipoMovimiento = vista.getTipoDocu().getSelectedItem().toString();
            int documentoId = vista.getDocumentoId();
            int cantidad = Integer.parseInt(vista.getCantidad().getText());
            String fecha = vista.getFecha().getText();
            String observacion = vista.getObs().getText();
            String nroDocumento = vista.getNdocumento().getText();
            ModeloMovimiento movimiento = new ModeloMovimiento(productoId, tipoMovimiento, documentoId,cantidad, fecha, observacion);

            boolean exito = new MovimientoDAO().registrarMovimiento(movimiento, nroDocumento);

            if (exito) {
                JOptionPane.showMessageDialog(null,
                        "Movimiento registrado exitosamente.",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarTablas();
            } else {
                JOptionPane.showMessageDialog(null,
                        "Error al registrar el movimiento.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }

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
    public void cargarTiposDocumentoEnCombo() {
        MovimientoDAO dao = new MovimientoDAO();
        List<String> tipos = dao.obtenerTiposDocumento();

        vista.getTipoDocu().removeAllItems(); //
        for (String tipo : tipos) {
            vista.getTipoDocu().addItem(tipo);
        }
    }
    public void cargarTablas() {
        MovimientoDAO dao = new MovimientoDAO();
        List<String[]> movimientos = dao.obtenerMovimientos();
        List<String[]> documentos = dao.obtenerDocumentos();
        vista.cargarTablaMovimiento(movimientos);
        vista.cargarTablaDocumento(documentos);
    }


}