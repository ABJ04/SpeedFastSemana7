package vista;

import dao.PedidoDAO;
import dao.RepartidorDAO;
import modelo.Pedido;
import modelo.Repartidor;
import modelo.ZonaDeCarga;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VentanaPrincipal extends JFrame {

    private JButton btnRegistrar;
    private JButton btnRegistrarRepartidor;
    private JButton btnListar;
    private JButton btnIniciarEntrega;

    public VentanaPrincipal() {

        setTitle("SpeedFast - Gestión de Entregas");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        btnRegistrar = new JButton("Registrar pedido");
        btnRegistrarRepartidor = new JButton("Registrar repartidor");
        btnListar = new JButton("Listar pedidos");
        btnIniciarEntrega =
                new JButton("Asignar repartidor / Iniciar entrega");

        btnRegistrar.addActionListener(e ->
                new VentanaRegistroPedido()
        );

        btnRegistrarRepartidor.addActionListener(e ->
                new VentanaRegistroRepartidor()
        );

        btnListar.addActionListener(e ->
                new VentanaListaPedidos()
        );

        btnIniciarEntrega.addActionListener(e ->
                iniciarEntregas()
        );

        setLayout(new GridLayout(4, 1, 10, 10));

        add(btnRegistrar);
        add(btnRegistrarRepartidor);
        add(btnListar);
        add(btnIniciarEntrega);

        setVisible(true);
    }

    private void iniciarEntregas() {

        PedidoDAO pedidoDAO = new PedidoDAO();
        RepartidorDAO repartidorDAO = new RepartidorDAO();

        List<Pedido> pedidosPendientes =
                pedidoDAO.listarPendientes();

        if (pedidosPendientes.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "No existen pedidos pendientes."
            );

            return;
        }

        List<Repartidor> repartidores =
                repartidorDAO.listarTodos();

        if (repartidores.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "No existen repartidores registrados."
            );

            return;
        }

        ZonaDeCarga zonaDeCarga = new ZonaDeCarga();

        for (Pedido pedido : pedidosPendientes) {
            zonaDeCarga.agregarPedido(pedido);
        }

        for (Repartidor repartidor : repartidores) {
            repartidor.setZonaDeCarga(zonaDeCarga);
        }

        ExecutorService executor =
                Executors.newFixedThreadPool(
                        repartidores.size()
                );

        for (Repartidor repartidor : repartidores) {
            executor.execute(repartidor);
        }

        executor.shutdown();

        JOptionPane.showMessageDialog(
                this,
                "Entregas iniciadas correctamente."
        );
    }
}