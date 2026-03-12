package com.oracle.taller.model;

public class EmpleadoPracticante extends Empleado {

    private double estipendio;

    public EmpleadoPracticante(String id, String nombre, String departamento, double estipendio) {
        super(id, nombre, departamento);
        this.estipendio = estipendio;
    }

    @Override
    public double calcularSalario() {
        return estipendio;
    }

    public double getEstipendio() { return estipendio; }
}
