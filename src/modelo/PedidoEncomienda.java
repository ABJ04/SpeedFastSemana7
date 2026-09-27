package modelo;

public class PedidoEncomienda extends Pedido {

    public PedidoEncomienda(int idPedido, String direccionEntrega, double distanciaKm) {
        super(idPedido, direccionEntrega, distanciaKm);
    }

    @Override
    public int calcularTiempoEntrega() {
        return (int) (20 + 1.5 * getDistanciaKm());
    }

    @Override
    public void asignarRepartidor() {
        setRepartidorAsignado("Carlos Soto");
        System.out.println("[Pedido Encomienda]");
        System.out.println("Validando peso y embalaje...");
        System.out.println("Repartidor asignado: Carlos Soto");
    }

    @Override
    public void asignarRepartidor(String nombre) {
        setRepartidorAsignado(nombre);
        System.out.println("[Pedido Encomienda]");
        System.out.println("Validando peso y embalaje...");
        System.out.println("Pedido asignado a " + nombre);
    }
}