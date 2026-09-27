package modelo;

public class PedidoComida extends Pedido {

    public PedidoComida(int idPedido, String direccionEntrega, double distanciaKm) {
        super(idPedido, direccionEntrega, distanciaKm);
    }

    @Override
    public int calcularTiempoEntrega() {
        return (int) (15 + 2 * getDistanciaKm());
    }

    @Override
    public void asignarRepartidor() {
        setRepartidorAsignado("Luis Díaz");
        System.out.println("[Pedido Comida]");
        System.out.println("Buscando repartidor con mochila térmica...");
        System.out.println("Repartidor asignado: Luis Díaz");
    }

    @Override
    public void asignarRepartidor(String nombre) {
        setRepartidorAsignado(nombre);
        System.out.println("[Pedido Comida]");
        System.out.println("Verificando mochila térmica...");
        System.out.println("Pedido asignado a " + nombre);
    }
}