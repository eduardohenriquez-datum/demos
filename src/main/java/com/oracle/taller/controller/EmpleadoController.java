package com.oracle.taller.controller;

import com.oracle.taller.service.EmpleadoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
                          @RequestParam(defaultValue = "0") double bono) {
        switch (tipo) {
            case "TIEMPO_COMPLETO" -> service.agregarTiempoCompleto(id, nombre, departamento, salarioBase, prestaciones);
            case "CONTRATISTA"     -> service.agregarContratista(id, nombre, departamento, tarifa, horas);
            case "GERENTE"         -> service.agregarGerente(id, nombre, departamento, salarioBase, prestaciones, bono);
        }
        return "redirect:/";
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam String id) {
        service.eliminar(id);
        return "redirect:/";
    }
}
