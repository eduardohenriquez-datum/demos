

/**
 * CONCEPTO: Herencia
 *
 * EmpleadoTiempoCompleto ES UN Empleado — hereda todos sus atributos
 * y métodos, y agrega los propios (salarioBase, prestaciones).
 *
 * "extends" significa: "tomo todo lo que tiene Empleado y le agrego más."
 */
public class EmpleadoTiempoCompleto extends Empleado {

    private double salarioBase;
    private double prestaciones; // porcentaje, ej: 0.35 = 35%

    // CONCEPTO: Constructor con super()
    // Llamamos al constructor del padre para inicializar los datos comunes.
    // Luego inicializamos los datos propios de esta clase.
    public EmpleadoTiempoCompleto(String id, String nombre, String departamento,
                                   double salarioBase, double prestaciones) {
        super(id, nombre, departamento); // llama al constructor de Empleado
        this.salarioBase = salarioBase;
        this.prestaciones = prestaciones;
    }

    // CONCEPTO: Implementación de método abstracto (Polimorfismo)
    // Aquí decidimos CÓMO calcula su salario un empleado de tiempo completo.
    // Salario total = salario base + prestaciones
    @Override
    public double calcularSalario() {
        return salarioBase + (salarioBase * prestaciones);
    }

    public double getSalarioBase() {
        return salarioBase;
    }

    public double getPrestaciones() {
        return prestaciones;
    }
}
