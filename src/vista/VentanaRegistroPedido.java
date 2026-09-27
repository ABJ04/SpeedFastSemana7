package vista;

import dao.PedidoDAO;
import modelo.Pedido;
import modelo.PedidoComida;
import modelo.PedidoEncomienda;
import modelo.PedidoExpress;

import javax.swing.*;
import java.awt.*;

public class VentanaRegistroPedido extends JFrame {

    private JTextField txtDireccion;
    private JComboBox<String> comboTipo;
    private JButton btnGuardar;

    public VentanaRegistroPedido() {

        setTitle("Registrar pedido");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(3, 2, 10, 10));

        txtDireccion = new JTextField();

        comboTipo = new JComboBox<>(
                new String[]{"Comida", "Encomienda", "Express"}
        );

        btnGuardar = new JButton("Guardar");

        add(new JLabel("Dirección:"));
        add(txtDireccion);

        add(new JLabel("Tipo:"));
        add(comboTipo);

        add(new JLabel(""));
        add(btnGuardar);

        btnGuardar.addActionListener(e -> guardarPedido());

        setVisible(true);
    }

    private void guardarPedido() {

        String direccion = txtDireccion.getText().trim();
        String tipo = comboTipo.getSelectedItem().toString();

        if (direccion.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Debes ingresar una dirección."
            );
            return;
        }

        Pedido pedido;

        if (tipo.equals("Comida")) {

            pedido = new PedidoComida(
                    0,
                    direccion,
                    0
            );

        } else if (tipo.equals("Encomienda")) {

            pedido = new PedidoEncomienda(
                    0,
                    direccion,
                    0
            );

        } else {

            pedido = new PedidoExpress(
                    0,
                    direccion,
                    0
            );
        }

        PedidoDAO pedidoDAO = new PedidoDAO();

        boolean guardado = pedidoDAO.guardar(pedido);

        if (guardado) {

            JOptionPane.showMessageDialog(
                    this,
                    "Pedido registrado correctamente en MySQL."
            );

            txtDireccion.setText("");

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo registrar el pedido."
            );
        }
    }
}