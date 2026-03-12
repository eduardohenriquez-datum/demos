package com.oracle.taller.model;

public abstract class Empleado implements Reportable {

    private String id;
    private String nombre;
    private String departamento;

    public Empleado(String id, String nombre, String departamento) {
        this.id = id;
        this.nombre = nombre;
        this.departamento = departamento;
    }

    public abstract double calcularSalario();

    public String getId()           { return id; }
    public String getNombre()       { return nombre; }
    public String getDepartamento() { return departamento; }

    public void setDepartamento(String departamento) {
        if (departamento != null && !departamento.isEmpty())
            this.departamento = departamento;
    }

    @Override
    public String generarReporte() {
        return String.format("[%s] %s | Depto: %s | Salario: $%.2f",
                id, nombre, departamento, calcularSalario());
    }
}
