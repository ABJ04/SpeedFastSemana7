package modelo;

import interfaces.Cancelable;
import interfaces.Despachable;
import interfaces.Rastreable;

import java.util.ArrayList;

public abstract class Pedido implements Despachable, Cancelable, Rastreable {

    private int idPedido;
    private String direccionEntrega;
    private double distanciaKm;
    private String repartidorAsignado;
    private boolean cancelado;
    private boolean despachado;
    private EstadoPedido estado;

    private static ArrayList<String> historialEntregas = new ArrayList<>();

    public Pedido(int idPedido, String direccionEntrega, double distanciaKm) {
        this.idPedido = idPedido;
        this.direccionEntrega = direccionEntrega;
        this.distanciaKm = distanciaKm;
        this.cancelado = false;
        this.despachado = false;
        this.estado = EstadoPedido.PENDIENTE;
    }

    public void mostrarResumen() {
        System.out.println("Pedido #" + idPedido);
        System.out.println("Dirección: " + direccionEntrega);
        System.out.println("Distancia: " + distanciaKm + " km");

        if (repartidorAsignado != null) {
            System.out.println("Repartidor asignado: " + repartidorAsignado);
        }
    }

    public abstract int calcularTiempoEntrega();

    public void reservarPedido() {
        System.out.println("Pedido #" + idPedido + " reservado correctamente.");
    }

    public void asignarRepartidor() {
        repartidorAsignado = "Repartidor automático";
        System.out.println("Repartidor asignado automáticamente.");
    }

    public void asignarRepartidor(String nombre) {
        repartidorAsignado = nombre;
        System.out.println("Pedido asignado a " + nombre);
    }

    @Override
    public void despachar() {
        if (cancelado) {
            System.out.println("No se puede despachar el Pedido #" + idPedido + " porque está cancelado.");
            return;
        }

        despachado = true;
        System.out.println("Pedido despachado correctamente.");
        historialEntregas.add(getClass().getSimpleName() + " #" + idPedido + " - entregado por " + repartidorAsignado);
    }

    @Override
    public void cancelar() {
        if (despachado) {
            System.out.println("El Pedido #" + idPedido + " ya fue despachado y no puede cancelarse.");
            return;
        }

        cancelado = true;
        System.out.println("Pedido #" + idPedido + " cancelado exitosamente.");
    }

    @Override
    public void verHistorial() {
        System.out.println("=== HISTORIAL DE ENTREGAS ===");

        if (historialEntregas.isEmpty()) {
            System.out.println("No existen entregas registradas.");
            return;
        }

        for (String entrega : historialEntregas) {
            System.out.println("- " + entrega);
        }
    }

    public double getDistanciaKm() {
        return distanciaKm;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public String getRepartidorAsignado() {
        return repartidorAsignado;
    }

    protected void setRepartidorAsignado(String repartidorAsignado) {
        this.repartidorAsignado = repartidorAsignado;
    }

    public String getDireccionEntrega() {
        return direccionEntrega;
    }

    public void setDireccionEntrega(String direccionEntrega) {
        this.direccionEntrega = direccionEntrega;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido nuevoEstado) {
        this.estado = nuevoEstado;
    }

    public void setEstado(String nuevoEstado) {
        this.estado = EstadoPedido.valueOf(nuevoEstado);
    }

    @Override
    public String toString() {
        return "Pedido #" + idPedido + " | Dirección: " + direccionEntrega + " | Estado: " + estado;
    }


}