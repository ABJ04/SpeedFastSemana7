package dao;

import modelo.Repartidor;
import modelo.ZonaDeCarga;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RepartidorDAO {

    public List<Repartidor> listarTodos() {

        List<Repartidor> repartidores = new ArrayList<>();

        String sql = "SELECT id, nombre FROM repartidor";

        try (
                Connection conexion = ConexionBD.conectar();
                PreparedStatement sentencia = conexion.prepareStatement(sql);
                ResultSet resultado = sentencia.executeQuery()
        ) {

            while (resultado.next()) {

                int id = resultado.getInt("id");
                String nombre = resultado.getString("nombre");

                Repartidor repartidor =
                        new Repartidor(id, nombre, new ZonaDeCarga());

                repartidores.add(repartidor);
            }

        } catch (SQLException e) {
            System.out.println(
                    "Error al listar repartidores: " + e.getMessage()
            );
        }

        return repartidores;

    }

    public boolean guardar(Repartidor repartidor) {

        String sql = "INSERT INTO repartidor (nombre) VALUES (?)";

        try (
                Connection conexion = ConexionBD.conectar();
                PreparedStatement sentencia = conexion.prepareStatement(sql)
        ) {

            sentencia.setString(1, repartidor.getNombre());

            sentencia.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.out.println("Error al guardar repartidor: " + e.getMessage());
            return false;
        }
    }

}