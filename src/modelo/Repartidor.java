package modelo;

import dao.EntregaDAO;
import dao.PedidoDAO;

import java.sql.Date;
import java.sql.Time;
import java.util.Random;

public class Repartidor implements Runnable {

    private int id;
    private String nombre;
    private ZonaDeCarga zonaDeCarga;

    public Repartidor(String nombre, ZonaDeCarga zonaDeCarga) {
        this.id = 0;
        this.nombre = nombre;
        this.zonaDeCarga = zonaDeCarga;
    }

    public Repartidor(int id, String nombre, ZonaDeCarga zonaDeCarga) {
        this.id = id;
        this.nombre = nombre;
        this.zonaDeCarga = zonaDeCarga;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setZonaDeCarga(ZonaDeCarga zonaDeCarga) {
        this.zonaDeCarga = zonaDeCarga;
    }

    @Override
    public void run() {

        Random random = new Random();

        PedidoDAO pedidoDAO = new PedidoDAO();
        EntregaDAO entregaDAO = new EntregaDAO();

        while (true) {

            Pedido pedido = zonaDeCarga.retirarPedido();

            if (pedido == null) {
                break;
            }

            try {

                System.out.println(
                        "[Repartidor - " + nombre +
                                "] Retirando pedido #" +
                                pedido.getIdPedido() + "..."
                );

                pedido.setEstado(EstadoPedido.EN_REPARTO);

                pedidoDAO.actualizarEstado(
                        pedido.getIdPedido(),
                        EstadoPedido.EN_REPARTO
                );

                System.out.println(
                        "[Repartidor - " + nombre +
                                "] Estado: " + pedido.getEstado()
                );

                System.out.println(
                        "[Repartidor - " + nombre +
                                "] Entregando pedido #" +
                                pedido.getIdPedido() + "..."
                );

                int tiempoEspera =
                        random.nextInt(2000) + 1000;

                Thread.sleep(tiempoEspera);

                pedido.setEstado(EstadoPedido.ENTREGADO);

                pedidoDAO.actualizarEstado(
                        pedido.getIdPedido(),
                        EstadoPedido.ENTREGADO
                );

                Entrega entrega = new Entrega(
                        pedido.getIdPedido(),
                        id,
                        new Date(System.currentTimeMillis()),
                        new Time(System.currentTimeMillis())
                );

                entregaDAO.guardar(entrega);

                System.out.println(
                        "[Repartidor - " + nombre +
                                "] Pedido #" +
                                pedido.getIdPedido() +
                                " entregado."
                );

                System.out.println(
                        "[Repartidor - " + nombre +
                                "] Estado: " + pedido.getEstado()
                );

                System.out.println();

            } catch (InterruptedException e) {

                System.out.println(
                        "[Repartidor - " + nombre +
                                "] fue interrumpido."
                );

                Thread.currentThread().interrupt();
                break;
            }
        }

        System.out.println(
                "[Repartidor - " + nombre +
                        "] no tiene más pedidos."
        );
    }
}