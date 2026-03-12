package com.oracle.taller.controller;

import com.oracle.taller.service.EmpleadoService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class EmpleadoController {

    private final EmpleadoService service;

    public EmpleadoController(EmpleadoService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("empleados", service.obtenerTodos());
        model.addAttribute("totalNomina", service.calcularTotalNomina());
        return "index";
    }

    @PostMapping("/agregar")
    public String agregar(@RequestParam String tipo,
                          @RequestParam String id,
                          @RequestParam String nombre,
                          @RequestParam String departamento,
                          @RequestParam(defaultValue = "0") double salarioBase,
                          @RequestParam(defaultValue = "0") double prestaciones,
                          @RequestParam(defaultValue = "0") double tarifa,
                          @RequestParam(defaultValue = "0") int horas,
                          @RequestParam(defaultValue = "0") double bono,
                          RedirectAttributes ra) {

        if (id.isBlank() || nombre.isBlank() || departamento.isBlank()) {
            ra.addFlashAttribute("error", "ID, nombre y departamento son obligatorios.");
            return "redirect:/";
        }

        switch (tipo) {
            case "TIEMPO_COMPLETO" -> {
                if (salarioBase <= 0 || prestaciones < 0) {
                    ra.addFlashAttribute("error", "Salario base debe ser mayor a 0.");
                    return "redirect:/";
                }
            }
            case "CONTRATISTA" -> {
                if (tarifa <= 0 || horas <= 0) {
                    ra.addFlashAttribute("error", "Tarifa y horas deben ser mayores a 0.");
                    return "redirect:/";
                }
            }
            case "GERENTE" -> {
                if (salarioBase <= 0 || prestaciones < 0 || bono < 0) {
                    ra.addFlashAttribute("error", "Salario base debe ser mayor a 0.");
                    return "redirect:/";
                }
            }
        }

        try {
            switch (tipo) {
                case "TIEMPO_COMPLETO" -> service.agregarTiempoCompleto(id, nombre, departamento, salarioBase, prestaciones);
                case "CONTRATISTA"     -> service.agregarContratista(id, nombre, departamento, tarifa, horas);
                case "GERENTE"         -> service.agregarGerente(id, nombre, departamento, salarioBase, prestaciones, bono);
            }
        } catch (DuplicateKeyException e) {
            ra.addFlashAttribute("error", "Ya existe un empleado con ID '" + id + "'.");
            return "redirect:/";
        }

        return "redirect:/";
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam String id) {
        service.eliminar(id);
        return "redirect:/";
    }
}
