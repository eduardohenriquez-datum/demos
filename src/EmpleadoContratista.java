

/**
 * CONCEPTO: Herencia
 *
 * EmpleadoContratista también ES UN Empleado, pero su forma de calcular
 * el salario es completamente diferente — por horas trabajadas.
 *
 * Esto demuestra por qué necesitamos el método abstracto calcularSalario()
 * en la clase padre: cada tipo tiene su propia lógica.
 */
public class EmpleadoContratista extends Empleado {

    private double tarifaPorHora;
    private int horasTrabajadas;

    public EmpleadoContratista(String id, String nombre, String departamento,
                                double tarifaPorHora, int horasTrabajadas) {
        super(id, nombre, departamento);
        this.tarifaPorHora = tarifaPorHora;
        this.horasTrabajadas = horasTrabajadas;
    }

    // CONCEPTO: Polimorfismo
    // Mismo método, lógica completamente diferente a EmpleadoTiempoCompleto.
    // El sistema no necesita saber qué tipo es — solo llama calcularSalario().
    @Override
    public double calcularSalario() {
        return tarifaPorHora * horasTrabajadas;
    }

    public double getTarifaPorHora() {
        return tarifaPorHora;
    }

    public int getHorasTrabajadas() {
        return horasTrabajadas;
    }

    public void setHorasTrabajadas(int horasTrabajadas) {
        if (horasTrabajadas >= 0) {
            this.horasTrabajadas = horasTrabajadas;
        }
    }

    @Override
    public String generarReporte() {
        return super.generarReporte() +
               String.format(" | Horas: %d | Tarifa: $%.2f/hr", horasTrabajadas, tarifaPorHora);
    }
}
