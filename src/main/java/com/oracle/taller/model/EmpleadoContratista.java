package com.oracle.taller.model;

public class EmpleadoContratista extends Empleado {

    private double tarifaPorHora;
    private int horasTrabajadas;

    public EmpleadoContratista(String id, String nombre, String departamento,
                                double tarifaPorHora, int horasTrabajadas) {
        super(id, nombre, departamento);
        this.tarifaPorHora = tarifaPorHora;
        this.horasTrabajadas = horasTrabajadas;
    }

    @Override
    public double calcularSalario() {
        return tarifaPorHora * horasTrabajadas;
    }

    public double getTarifaPorHora()  { return tarifaPorHora; }
    public int getHorasTrabajadas()   { return horasTrabajadas; }

    @Override
    public String generarReporte() {
        return super.generarReporte() +
               String.format(" | Horas: %d | Tarifa: $%.2f/hr", horasTrabajadas, tarifaPorHora);
    }
}
