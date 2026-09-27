package modelo;

public class PedidoExpress extends Pedido {

    public PedidoExpress(int idPedido, String direccionEntrega, double distanciaKm) {
        super(idPedido, direccionEntrega, distanciaKm);
    }

    @Override
    public int calcularTiempoEntrega() {
        if (getDistanciaKm() > 5) {
            return 15;
        }

        return 10;
    }

    @Override
    public void asignarRepartidor() {
        setRepartidorAsignado("Repartidor Express");
        System.out.println("[Pedido Express]");
        System.out.println("Buscando repartidor más cercano...");
        System.out.println("Repartidor Express asignado.");
    }

    @Override
    public void asignarRepartidor(String nombre) {
        setRepartidorAsignado(nombre);
        System.out.println("[Pedido Express]");
        System.out.println("Asignación prioritaria...");
        System.out.println("Pedido asignado a " + nombre);
    }
}