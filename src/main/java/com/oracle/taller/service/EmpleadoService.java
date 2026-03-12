package com.oracle.taller.service;

import com.oracle.taller.dao.EmpleadoDAO;
import com.oracle.taller.model.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpleadoService {

    private final EmpleadoDAO dao;

    public EmpleadoService(EmpleadoDAO dao) {
        this.dao = dao;
    }

    public List<Empleado> obtenerTodos() {
        return dao.obtenerTodos();
    }

    public void agregarTiempoCompleto(String id, String nombre, String depto,
            double salarioBase, double prestaciones) {
        dao.guardar(new EmpleadoTiempoCompleto(id, nombre, depto, salarioBase, prestaciones));
    }

    public void agregarContratista(String id, String nombre, String depto,
            double tarifa, int horas) {
        dao.guardar(new EmpleadoContratista(id, nombre, depto, tarifa, horas));
    }

    public void agregarPracticante(String id, String nombre, String depto, double estipendio) {
        dao.guardar(new EmpleadoPracticante(id, nombre, depto, estipendio));
    }

    public void agregarGerente(String id, String nombre, String depto,
            double salarioBase, double prestaciones, double bono) {
        dao.guardar(new Gerente(id, nombre, depto, salarioBase, prestaciones, bono));
    }

    public void eliminar(String id) {
        dao.eliminar(id);
    }

    public double calcularTotalNomina() {
        return dao.obtenerTodos().stream()
                .mapToDouble(Empleado::calcularSalario)
                .sum();
    }
}
