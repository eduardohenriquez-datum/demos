package com.oracle.taller.model;

import java.util.ArrayList;
import java.util.List;

public class Gerente extends EmpleadoTiempoCompleto {

    private double bono;
    private List<Empleado> equipo = new ArrayList<>();

    public Gerente(String id, String nombre, String departamento,
                   double salarioBase, double prestaciones, double bono) {
        super(id, nombre, departamento, salarioBase, prestaciones);
        this.bono = bono;
    }

    @Override
    public double calcularSalario() {
        return super.calcularSalario() + bono;
    }

    public double getBono()              { return bono; }
    public List<Empleado> getEquipo()    { return equipo; }

    @Override
    public String generarReporte() {
        return super.generarReporte() +
               String.format(" | Bono: $%.2f", bono);
    }
}
