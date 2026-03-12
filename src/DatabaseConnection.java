

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * CONCEPTO: Patrón Singleton
 *
 * Garantiza que solo exista UNA conexión a la base de datos
 * en toda la aplicación. Si alguien pide la conexión y ya existe,
 * devuelve la misma — no abre una nueva.
 *
 * Es un patrón de diseño muy común en desarrollo empresarial.
 *
 * En producción con Oracle:
 * Solo cambias la URL en database.properties — el código no cambia.
 */
public class DatabaseConnection {

    private static Connection instance;

    // Constructor privado — nadie puede hacer new DatabaseConnection()
    private DatabaseConnection() {}

    public static Connection getInstance() throws Exception {
        if (instance == null || instance.isClosed()) {
            Properties props = new Properties();

            // Carga la configuración desde el archivo de propiedades
            try (InputStream is = DatabaseConnection.class
                    .getClassLoader().getResourceAsStream("database.properties")) {
                if (is == null) {
                    throw new RuntimeException("No se encontró database.properties");
                }
                props.load(is);
            }

            String url      = props.getProperty("db.url");
            String user     = props.getProperty("db.user");
            String password = props.getProperty("db.password");

            instance = DriverManager.getConnection(url, user, password);
            System.out.println("Conexión establecida: " + url);
        }
        return instance;
    }

    public static void cerrar() {
        try {
            if (instance != null && !instance.isClosed()) {
                instance.close();
                System.out.println("Conexión cerrada.");
            }
        } catch (Exception e) {
            System.err.println("Error al cerrar conexión: " + e.getMessage());
        }
    }
}
