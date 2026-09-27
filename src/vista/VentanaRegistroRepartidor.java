package vista;

import dao.RepartidorDAO;
import modelo.Repartidor;
import modelo.ZonaDeCarga;

import javax.swing.*;
import java.awt.*;

public class VentanaRegistroRepartidor extends JFrame {

    private JTextField txtNombre;
    private JButton btnGuardar;

    public VentanaRegistroRepartidor() {

        setTitle("Registrar repartidor");
        setSize(400, 150);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(2, 2, 10, 10));

        txtNombre = new JTextField();
        btnGuardar = new JButton("Guardar");

        add(new JLabel("Nombre:"));
        add(txtNombre);

        add(new JLabel(""));
        add(btnGuardar);

        btnGuardar.addActionListener(e -> guardarRepartidor());

        setVisible(true);
    }

    private void guardarRepartidor() {

        String nombre = txtNombre.getText().trim();

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Debes ingresar el nombre del repartidor."
            );
            return;
        }

        Repartidor repartidor =
                new Repartidor(nombre, new ZonaDeCarga());

        RepartidorDAO repartidorDAO = new RepartidorDAO();

        boolean guardado = repartidorDAO.guardar(repartidor);

        if (guardado) {

            JOptionPane.showMessageDialog(
                    this,
                    "Repartidor registrado correctamente en MySQL."
            );

            txtNombre.setText("");

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo registrar el repartidor."
            );
        }
    }
}