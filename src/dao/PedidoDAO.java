package dao;

import modelo.EstadoPedido;
import modelo.Pedido;
import modelo.PedidoComida;
import modelo.PedidoEncomienda;
import modelo.PedidoExpress;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {

    public boolean guardar(Pedido pedido) {

        String sql = "INSERT INTO pedido (direccion, tipo, estado) VALUES (?, ?, ?)";

        try (
                Connection conexion = ConexionBD.conectar();
                PreparedStatement sentencia = conexion.prepareStatement(sql)
        ) {

            sentencia.setString(1, pedido.getDireccionEntrega());

            String tipo = pedido.getClass()
                    .getSimpleName()
                    .replace("Pedido", "")
                    .toUpperCase();

            sentencia.setString(2, tipo);
            sentencia.setString(3, pedido.getEstado().name());

            sentencia.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.out.println(
                    "Error al guardar pedido: " + e.getMessage()
            );
            return false;
        }
    }

    public List<Object[]> listarTodos() {

        List<Object[]> pedidos = new ArrayList<>();

        String sql = """
                SELECT id, direccion, tipo, estado
                FROM pedido
                ORDER BY id
                """;

        try (
                Connection conexion = ConexionBD.conectar();
                PreparedStatement sentencia = conexion.prepareStatement(sql);
                ResultSet resultado = sentencia.executeQuery()
        ) {

            while (resultado.next()) {

                Object[] fila = {
                        resultado.getInt("id"),
                        resultado.getString("direccion"),
                        resultado.getString("tipo"),
                        resultado.getString("estado")
                };

                pedidos.add(fila);
            }

        } catch (SQLException e) {
            System.out.println(
                    "Error al listar pedidos: " + e.getMessage()
            );
        }

        return pedidos;
    }

    public List<Pedido> listarPendientes() {

        List<Pedido> pedidos = new ArrayList<>();

        String sql = """
                SELECT id, direccion, tipo, estado
                FROM pedido
                WHERE estado = 'PENDIENTE'
                ORDER BY id
                """;

        try (
                Connection conexion = ConexionBD.conectar();
                PreparedStatement sentencia = conexion.prepareStatement(sql);
                ResultSet resultado = sentencia.executeQuery()
        ) {

            while (resultado.next()) {

                int id = resultado.getInt("id");
                String direccion = resultado.getString("direccion");
                String tipo = resultado.getString("tipo");

                Pedido pedido;

                if (tipo.equals("COMIDA")) {

                    pedido = new PedidoComida(
                            id,
                            direccion,
                            0
                    );

                } else if (tipo.equals("ENCOMIENDA")) {

                    pedido = new PedidoEncomienda(
                            id,
                            direccion,
                            0
                    );

                } else {

                    pedido = new PedidoExpress(
                            id,
                            direccion,
                            0
                    );
                }

                pedidos.add(pedido);
            }

        } catch (SQLException e) {
            System.out.println(
                    "Error al listar pedidos pendientes: "
                            + e.getMessage()
            );
        }

        return pedidos;
    }

    public boolean actualizarEstado(int idPedido, EstadoPedido estado) {

        String sql = """
                UPDATE pedido
                SET estado = ?
                WHERE id = ?
                """;

        try (
                Connection conexion = ConexionBD.conectar();
                PreparedStatement sentencia = conexion.prepareStatement(sql)
        ) {

            sentencia.setString(1, estado.name());
            sentencia.setInt(2, idPedido);

            sentencia.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.out.println(
                    "Error al actualizar estado del pedido: "
                            + e.getMessage()
            );
            return false;
        }
    }
}