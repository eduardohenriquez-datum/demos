package com.oracle.taller.model;

public class EmpleadoTiempoCompleto extends Empleado {

    private double salarioBase;
    private double prestaciones;

    public EmpleadoTiempoCompleto(String id, String nombre, String departamento,
                                   double salarioBase, double prestaciones) {
        super(id, nombre, departamento);
        this.salarioBase = salarioBase;
        this.prestaciones = prestaciones;
    }

    @Override
    public double calcularSalario() {
        return salarioBase + (salarioBase * prestaciones);
    }

    public double getSalarioBase()   { return salarioBase; }
    public double getPrestaciones()  { return prestaciones; }
}
