

import java.util.List;

/**
 * Gestiona la colección de empleados.
 * Usa EmpleadoDAO para persistir — no sabe nada de SQL, solo pide y recibe objetos.
 */
public class SistemaEmpleados {

    private List<Empleado> empleados;
    private String nombreEmpresa;
    private EmpleadoDAO dao;

    public SistemaEmpleados(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
        this.dao = new EmpleadoDAO();
        this.empleados = dao.obtenerTodos(); // carga datos persistidos al iniciar
        if (!empleados.isEmpty()) {
            System.out.println("(" + empleados.size() + " empleados cargados de la BD)");
        }
    }

    public void agregarEmpleado(Empleado empleado) {
        dao.guardar(empleado);
        empleados.add(empleado);
        System.out.println("Empleado agregado: " + empleado.getNombre());
    }

    public Empleado buscarPorId(String id) {
        return empleados.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    /**
     * CONCEPTO: Polimorfismo en acción
     *
     * Llama calcularSalario() sin saber el tipo exacto.
     * Java ejecuta automáticamente la versión correcta de cada objeto.
     */
    public void imprimirNomina() {
        System.out.println("\n===== NÓMINA: " + nombreEmpresa + " =====");
        double totalNomina = 0;

        for (Empleado e : empleados) {
            System.out.println(e.generarReporte());
            totalNomina += e.calcularSalario();
        }

        System.out.printf("%nTOTAL NÓMINA: $%.2f%n", totalNomina);
        System.out.println("=".repeat(40));
    }

    public void imprimirResumen() {
        long tiempoCompleto = empleados.stream()
                .filter(e -> e instanceof EmpleadoTiempoCompleto && !(e instanceof Gerente))
                .count();
        long contratistas = empleados.stream()
                .filter(e -> e instanceof EmpleadoContratista)
                .count();
        long gerentes = empleados.stream()
                .filter(e -> e instanceof Gerente)
                .count();

        System.out.println("\n===== RESUMEN =====");
        System.out.println("Tiempo completo : " + tiempoCompleto);
        System.out.println("Contratistas    : " + contratistas);
        System.out.println("Gerentes        : " + gerentes);
        System.out.println("Total           : " + empleados.size());
    }

    public int getTotalEmpleados() {
        return empleados.size();
    }
}
