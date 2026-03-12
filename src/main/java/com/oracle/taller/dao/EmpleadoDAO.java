package com.oracle.taller.dao;

import com.oracle.taller.model.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class EmpleadoDAO {

    private final JdbcTemplate jdbc;

    public EmpleadoDAO(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public void guardar(Empleado emp) {
        String sql = """
                INSERT INTO empleados
                    (id, nombre, departamento, tipo,
                     salario_base, prestaciones, tarifa_hora, horas_trabajadas, bono)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;
        if (emp instanceof Gerente g) {
            jdbc.update(sql, g.getId(), g.getNombre(), g.getDepartamento(),
                    "GERENTE", g.getSalarioBase(), g.getPrestaciones(), null, null, g.getBono());

        } else if (emp instanceof EmpleadoTiempoCompleto etc) {
            jdbc.update(sql, etc.getId(), etc.getNombre(), etc.getDepartamento(),
                    "TIEMPO_COMPLETO", etc.getSalarioBase(), etc.getPrestaciones(), null, null, null);

        } else if (emp instanceof EmpleadoContratista ec) {
            jdbc.update(sql, ec.getId(), ec.getNombre(), ec.getDepartamento(),
                    "CONTRATISTA", null, null, ec.getTarifaPorHora(), ec.getHorasTrabajadas(), null);
        }
    }

    public void eliminar(String id) {
        jdbc.update("DELETE FROM empleados WHERE id = ?", id);
    }

    public List<Empleado> obtenerTodos() {
        return jdbc.query("SELECT * FROM empleados", this::mapear);
    }

    private Empleado mapear(ResultSet rs, int rowNum) throws SQLException {
        String id    = rs.getString("id");
        String nombre = rs.getString("nombre");
        String depto  = rs.getString("departamento");

        return switch (rs.getString("tipo")) {
            case "TIEMPO_COMPLETO" -> new EmpleadoTiempoCompleto(id, nombre, depto,
                    rs.getDouble("salario_base"), rs.getDouble("prestaciones"));
            case "CONTRATISTA"     -> new EmpleadoContratista(id, nombre, depto,
                    rs.getDouble("tarifa_hora"), rs.getInt("horas_trabajadas"));
            case "GERENTE"         -> new Gerente(id, nombre, depto,
                    rs.getDouble("salario_base"), rs.getDouble("prestaciones"), rs.getDouble("bono"));
            default -> null;
        };
    }
}
