package modelo;

import java.util.ArrayList;
import java.util.List;

public class ZonaDeCarga {

    private final List<Pedido> pedidos = new ArrayList<>();

    public ZonaDeCarga() {
        System.out.println("[Zona de carga inicializada]");
    }

    public synchronized void agregarPedido(Pedido pedido) {
        pedidos.add(pedido);
        System.out.println("Pedido #" + pedido.getIdPedido() + " agregado. Destino: " + pedido.getDireccionEntrega());
    }

    public synchronized Pedido retirarPedido() {
        if (pedidos.isEmpty()) {
            return null;
        }

        return pedidos.remove(0);
    }

    public synchronized boolean estaVacia() {
        return pedidos.isEmpty();
    }
}