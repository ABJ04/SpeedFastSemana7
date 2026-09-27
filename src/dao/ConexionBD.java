package dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexionBD {

    public static Connection conectar() throws SQLException {

        Properties propiedades = new Properties();

        try (FileInputStream archivo = new FileInputStream("db.properties")) {

            propiedades.load(archivo);

            String url = propiedades.getProperty("db.url");
            String usuario = propiedades.getProperty("db.user");
            String password = propiedades.getProperty("db.password");

            return DriverManager.getConnection(url, usuario, password);

        } catch (IOException e) {
            throw new SQLException("No se pudo leer db.properties", e);
        }
    }
}