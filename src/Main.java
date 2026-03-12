

import java.sql.*;
import java.util.Scanner;

/**
 * App interactiva — Sistema de Gestión de Empleados
 *
 * Menú de consola para crear y gestionar empleados.
 * Los datos se persisten en base de datos — al reiniciar, se recuperan.
 */
public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final SistemaEmpleados sistema = new SistemaEmpleados("Oracle");

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("  Sistema de Gestión de Empleados");
        System.out.println("  Taller POO — Oracle");
        System.out.println("========================================");

        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Elige una opción: ");

            switch (opcion) {
                case 1 -> agregarEmpleadoTiempoCompleto();
                case 2 -> agregarContratista();
                case 3 -> agregarGerente();
                case 4 -> sistema.imprimirNomina();
                case 5 -> sistema.imprimirResumen();
                case 6 -> buscarEmpleado();
                case 7 -> consultarSQL();
                case 0 -> System.out.println("\nHasta luego.");
                default -> System.out.println("Opción no válida.");
            }
        } while (opcion != 0);

        DatabaseConnection.cerrar();
    }

    // ─────────────────────────────────────────
    //  MENÚ
    // ─────────────────────────────────────────

    private static void mostrarMenu() {
        System.out.println("\n─── MENÚ ───────────────────────────────");
        System.out.println(" 1. Agregar Empleado de Tiempo Completo");
        System.out.println(" 2. Agregar Contratista");
        System.out.println(" 3. Agregar Gerente");
        System.out.println(" 4. Ver Nómina");
        System.out.println(" 5. Ver Resumen");
        System.out.println(" 6. Buscar Empleado");
        System.out.println(" 7. Consulta SQL");
        System.out.println(" 0. Salir");
        System.out.println("────────────────────────────────────────");
    }

    // ─────────────────────────────────────────
    //  AGREGAR EMPLEADOS
    // ─────────────────────────────────────────

    private static void agregarEmpleadoTiempoCompleto() {
        System.out.println("\n-- Nuevo Empleado de Tiempo Completo --");
        String id           = leerTexto("ID          : ");
        String nombre       = leerTexto("Nombre      : ");
        String depto        = leerTexto("Departamento: ");
        double salario      = leerDecimal("Salario base: $");
        double prestaciones = leerDecimal("Prestaciones (ej. 0.35 para 35%): ");

        EmpleadoTiempoCompleto emp = new EmpleadoTiempoCompleto(
                id, nombre, depto, salario, prestaciones);
        sistema.agregarEmpleado(emp);
        System.out.printf("→ Salario total: $%.2f%n", emp.calcularSalario());
    }

    private static void agregarContratista() {
        System.out.println("\n-- Nuevo Contratista --");
        String id     = leerTexto("ID          : ");
        String nombre = leerTexto("Nombre      : ");
        String depto  = leerTexto("Departamento: ");
        double tarifa = leerDecimal("Tarifa/hora : $");
        int horas     = leerEntero("Horas mes   : ");

        EmpleadoContratista emp = new EmpleadoContratista(
                id, nombre, depto, tarifa, horas);
        sistema.agregarEmpleado(emp);
        System.out.printf("→ Pago este mes: $%.2f%n", emp.calcularSalario());
    }

    private static void agregarGerente() {
        System.out.println("\n-- Nuevo Gerente --");
        String id           = leerTexto("ID          : ");
        String nombre       = leerTexto("Nombre      : ");
        String depto        = leerTexto("Departamento: ");
        double salario      = leerDecimal("Salario base: $");
        double prestaciones = leerDecimal("Prestaciones (ej. 0.35): ");
        double bono         = leerDecimal("Bono        : $");

        Gerente gerente = new Gerente(id, nombre, depto, salario, prestaciones, bono);
        sistema.agregarEmpleado(gerente);
        System.out.printf("→ Salario total: $%.2f%n", gerente.calcularSalario());
    }

    // ─────────────────────────────────────────
    //  BUSCAR
    // ─────────────────────────────────────────

    private static void buscarEmpleado() {
        String id = leerTexto("\nID a buscar: ");
        Empleado encontrado = sistema.buscarPorId(id);
        if (encontrado != null) {
            System.out.println("\n" + encontrado.generarReporte());
        } else {
            System.out.println("No se encontró ningún empleado con ID: " + id);
        }
    }

    // ─────────────────────────────────────────
    //  CONSULTA SQL
    // ─────────────────────────────────────────

    private static void consultarSQL() {
        System.out.println("\n-- Consulta SQL --");
        System.out.println("(escribe 'salir' para volver al menú)");

        while (true) {
            String sql = leerTexto("\nSQL> ");
            if (sql.equalsIgnoreCase("salir") || sql.isEmpty()) break;

            try (Connection con = DatabaseConnection.getInstance();
                 Statement stmt = con.createStatement()) {

                if (sql.trim().toUpperCase().startsWith("SELECT")) {
                    ResultSet rs = stmt.executeQuery(sql);
                    ResultSetMetaData meta = rs.getMetaData();
                    int cols = meta.getColumnCount();

                    // encabezado
                    for (int i = 1; i <= cols; i++) {
                        System.out.printf("%-20s", meta.getColumnName(i));
                    }
                    System.out.println();
                    System.out.println("-".repeat(cols * 20));

                    // filas
                    int filas = 0;
                    while (rs.next()) {
                        for (int i = 1; i <= cols; i++) {
                            System.out.printf("%-20s", rs.getString(i));
                        }
                        System.out.println();
                        filas++;
                    }
                    System.out.println("\n" + filas + " fila(s).");

                } else {
                    int affected = stmt.executeUpdate(sql);
                    System.out.println("OK — " + affected + " fila(s) afectadas.");
                }

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    // ─────────────────────────────────────────
    //  HELPERS DE ENTRADA
    // ─────────────────────────────────────────

    private static String leerTexto(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static int leerEntero(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("  Ingresa un número entero válido.");
            }
        }
    }

    private static double leerDecimal(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("  Ingresa un número válido (ej. 3000.50).");
            }
        }
    }
}

/*
 * =====================================================
 * EJERCICIO PARA LOS ALUMNOS
 * =====================================================
 *
 * 1. Crear EmpleadoPracticante.java
 *    - Extiende Empleado
 *    - Atributos: beca (double), universidad (String)
 *    - calcularSalario() retorna la beca
 *    - generarReporte() incluye la universidad
 *
 * 2. Agregar opción 7 al menú: "Agregar Practicante"
 *    - Crear el método agregarPracticante()
 *
 * 3. Agregar soporte en EmpleadoDAO:
 *    - En guardar(): nuevo caso instanceof EmpleadoPracticante
 *    - En reconstruir(): nuevo case "PRACTICANTE"
 *    - Nota: necesitarás agregar columna 'beca' y 'universidad' a la tabla
 *      o reutilizar columnas existentes (ej. salario_base = beca)
 *
 * Pregunta: ¿Hay que modificar SistemaEmpleados para que
 *           el practicante aparezca en la nómina?
 * =====================================================
 */
