package Controlador;

import Modelo.ModeloCategoria;
import Vista.VistaCategoria;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class ControladorCategoria extends JFrame {
    private VistaCategoria vista;
    private ModeloCategoria modelo;

    public ControladorCategoria(VistaCategoria vista, ModeloCategoria modelo) {
        this.vista = vista;
        this.modelo = modelo;

        this.vista.getCrearButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarCategoria();
            }
        });
    }

    private void guardarCategoria() {
        try {
            String nombre = vista.getNombre().getText().trim();

            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "El nombre no puede estar vacío.");
                return;
            }

            boolean creada = modelo.insertarCategoria(nombre);
            if (creada) {
                JOptionPane.showMessageDialog(vista, "Categoría guardada con éxito.");
                vista.dispose();
            } else {
                JOptionPane.showMessageDialog(vista, "Ya existe una categoría con ese nombre.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(vista, "Error al guardar la categoría.");
            e.printStackTrace();
        }
    }


    public void iniciarVista() {
        vista.mostrarVista();
    }
}

