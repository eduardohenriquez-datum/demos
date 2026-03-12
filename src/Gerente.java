

import java.util.ArrayList;
import java.util.List;

/**
 * CONCEPTO: Herencia en cadena
 *
 * Gerente extiende EmpleadoTiempoCompleto, que a su vez extiende Empleado.
 * Esto es una cadena de herencia: Gerente → EmpleadoTiempoCompleto → Empleado
 *
 * Un Gerente tiene todo lo de un empleado de tiempo completo,
 * más un bono y la responsabilidad de gestionar un equipo.
 */
public class Gerente extends EmpleadoTiempoCompleto {

    private double bono;
    private List<Empleado> equipo;

    public Gerente(String id, String nombre, String departamento,
                   double salarioBase, double prestaciones, double bono) {
        super(id, nombre, departamento, salarioBase, prestaciones);
        this.bono = bono;
        this.equipo = new ArrayList<>();
    }

    // CONCEPTO: Polimorfismo — sobreescritura
    // El gerente calcula su salario diferente: incluye el bono.
    // Reutilizamos el cálculo del padre con super.calcularSalario()
    @Override
    public double calcularSalario() {
        return super.calcularSalario() + bono;
    }

    public void agregarAlEquipo(Empleado empleado) {
        equipo.add(empleado);
    }

    public List<Empleado> getEquipo() {
        return equipo;
    }

    public int getTamanoEquipo() {
        return equipo.size();
    }

    public double getBono() {
        return bono;
    }

    @Override
    public String generarReporte() {
        return super.generarReporte() +
               String.format(" | Bono: $%.2f | Equipo: %d personas", bono, equipo.size());
    }
}
