

/**
 * CONCEPTO: Clase Abstracta (Abstracción + Herencia)
 *
 * Una clase abstracta no se puede instanciar directamente — nadie puede
 * crear un "Empleado genérico". Siempre debe ser un tipo específico
 * (tiempo completo, contratista, etc.).
 *
 * Define los atributos y comportamientos COMUNES a todos los empleados.
 * Los métodos abstractos obligan a las subclases a implementar su propia versión.
 */
public abstract class Empleado implements Reportable {

    // CONCEPTO: Encapsulamiento
    // Los atributos son privados — nadie puede modificarlos directamente.
    // Solo se accede a ellos a través de getters y setters.
    private String id;
    private String nombre;
    private String departamento;

    // CONCEPTO: Constructor
    // Se ejecuta al crear un objeto. Garantiza que todo empleado
    // tenga datos básicos desde el momento en que existe.
    public Empleado(String id, String nombre, String departamento) {
        this.id = id;
        this.nombre = nombre;
        this.departamento = departamento;
    }

    // CONCEPTO: Método abstracto (Polimorfismo)
    // Cada tipo de empleado calcula su salario de forma diferente.
    // Esta clase dice "todos los empleados pueden calcular su salario"
    // pero cada subclase decide CÓMO.
    public abstract double calcularSalario();

    // CONCEPTO: Getters (Encapsulamiento)
    // Permiten leer los datos sin exponerlos directamente.
    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDepartamento() {
        return departamento;
    }

    // CONCEPTO: Setter con validación
    // Controlamos qué datos se pueden modificar y cómo.
    public void setDepartamento(String departamento) {
        if (departamento != null && !departamento.isEmpty()) {
            this.departamento = departamento;
        }
    }

    // Implementación base del reporte — las subclases pueden extenderla
    @Override
    public String generarReporte() {
        return String.format("[%s] %s | Depto: %s | Salario: $%.2f",
                id, nombre, departamento, calcularSalario());
    }
}
