

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * CONCEPTO: Data Access Object (DAO)
 *
 * Separa la lógica de base de datos del resto del código.
 * El resto de la aplicación no sabe si los datos vienen de Oracle,
 * H2, un archivo, o cualquier otra fuente — solo llama al DAO.
 *
 * Esto es encapsulamiento aplicado a la capa de datos.
 */
public class EmpleadoDAO {

    public EmpleadoDAO() {
        inicializarTabla();
    }

    // Crea la tabla si no existe al arrancar la aplicación
    private void inicializarTabla() {
        String sql = """
                CREATE TABLE IF NOT EXISTS empleados (
                    id               VARCHAR(10)  PRIMARY KEY,
                    nombre           VARCHAR(100) NOT NULL,
                    departamento     VARCHAR(50)  NOT NULL,
                    tipo             VARCHAR(20)  NOT NULL,
                    salario_base     DECIMAL(10,2),
                    prestaciones     DECIMAL(5,4),
                    tarifa_hora      DECIMAL(10,2),
                    horas_trabajadas INTEGER,
                    bono             DECIMAL(10,2)
                )
                """;
        try (Connection con = DatabaseConnection.getInstance();
             Statement stmt = con.createStatement()) {
            stmt.execute(sql);
        } catch (Exception e) {
            System.err.println("Error al inicializar tabla: " + e.getMessage());
        }
    }

    /**
     * Guarda un empleado en la base de datos.
     * Detecta el tipo y extrae los campos correspondientes.
     */
    public void guardar(Empleado emp) {
        String sql = """
                INSERT INTO empleados
                    (id, nombre, departamento, tipo,
                     salario_base, prestaciones, tarifa_hora, horas_trabajadas, bono)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;
        try (Connection con = DatabaseConnection.getInstance();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, emp.getId());
            ps.setString(2, emp.getNombre());
            ps.setString(3, emp.getDepartamento());

            // CONCEPTO: instanceof — identificar el tipo real en tiempo de ejecución
            if (emp instanceof Gerente g) {
                ps.setString(4, "GERENTE");
                ps.setDouble(5, g.getSalarioBase());
                ps.setDouble(6, g.getPrestaciones());
                ps.setNull(7, Types.DECIMAL);
                ps.setNull(8, Types.INTEGER);
                ps.setDouble(9, g.getBono());

            } else if (emp instanceof EmpleadoTiempoCompleto etc) {
                ps.setString(4, "TIEMPO_COMPLETO");
                ps.setDouble(5, etc.getSalarioBase());
                ps.setDouble(6, etc.getPrestaciones());
                ps.setNull(7, Types.DECIMAL);
                ps.setNull(8, Types.INTEGER);
                ps.setNull(9, Types.DECIMAL);

            } else if (emp instanceof EmpleadoContratista ec) {
                ps.setString(4, "CONTRATISTA");
                ps.setNull(5, Types.DECIMAL);
                ps.setNull(6, Types.DECIMAL);
                ps.setDouble(7, ec.getTarifaPorHora());
                ps.setInt(8, ec.getHorasTrabajadas());
                ps.setNull(9, Types.DECIMAL);
            }

            ps.executeUpdate();

        } catch (Exception e) {
            System.err.println("Error al guardar empleado: " + e.getMessage());
        }
    }

    /**
     * Recupera todos los empleados de la BD y reconstruye los objetos Java.
     * Aquí el polimorfismo funciona al revés: de datos planos a objetos.
     */
    public List<Empleado> obtenerTodos() {
        List<Empleado> lista = new ArrayList<>();
        String sql = "SELECT * FROM empleados";

        try (Connection con = DatabaseConnection.getInstance();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Empleado emp = reconstruir(rs);
                if (emp != null) lista.add(emp);
            }

        } catch (Exception e) {
            System.err.println("Error al obtener empleados: " + e.getMessage());
        }
        return lista;
    }

    public Empleado buscarPorId(String id) {
        String sql = "SELECT * FROM empleados WHERE id = ?";
        try (Connection con = DatabaseConnection.getInstance();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return reconstruir(rs);
            }

        } catch (Exception e) {
            System.err.println("Error al buscar empleado: " + e.getMessage());
        }
        return null;
    }

    // Convierte una fila del ResultSet en el objeto Java correcto
    private Empleado reconstruir(ResultSet rs) throws SQLException {
        String id    = rs.getString("id");
        String nombre = rs.getString("nombre");
        String depto  = rs.getString("departamento");
        String tipo   = rs.getString("tipo");

        return switch (tipo) {
            case "TIEMPO_COMPLETO" -> new EmpleadoTiempoCompleto(
                    id, nombre, depto,
                    rs.getDouble("salario_base"),
                    rs.getDouble("prestaciones"));

            case "CONTRATISTA" -> new EmpleadoContratista(
                    id, nombre, depto,
                    rs.getDouble("tarifa_hora"),
                    rs.getInt("horas_trabajadas"));

            case "GERENTE" -> new Gerente(
                    id, nombre, depto,
                    rs.getDouble("salario_base"),
                    rs.getDouble("prestaciones"),
                    rs.getDouble("bono"));

            default -> null;
        };
    }
}
