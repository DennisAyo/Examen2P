package com.examen.examen2p.controller;

import com.model.Branch;
import com.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/branches")
@Tag(name = "Branch API", description = "API para gestionar sucursales bancarias y sus feriados")
public class BranchController {

    @Autowired
    private BranchService branchService;

    @GetMapping
    @Operation(summary = "Obtener todas las sucursales", description = "Retorna un listado de todas las sucursales bancarias")
    public ResponseEntity<List<Branch>> getAllBranches() {
        return ResponseEntity.ok(branchService.getAllBranches());
    }

    @PostMapping
    @Operation(summary = "Crear una nueva sucursal", description = "Crea una nueva sucursal sin feriados")
    public ResponseEntity<Branch> createBranch(@RequestBody Branch branch) {
        return ResponseEntity.ok(branchService.createBranch(branch));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener sucursal por ID", description = "Busca y retorna una sucursal por su ID")
    public ResponseEntity<Branch> getBranchById(@PathVariable String id) {
        return branchService.getBranchById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/phone")
    @Operation(summary = "Actualizar teléfono de sucursal", description = "Actualiza el número de teléfono de una sucursal")
    public ResponseEntity<Branch> updateBranchPhone(
            @PathVariable String id,
            @RequestParam String newPhoneNumber) {
        Branch updatedBranch = branchService.updateBranchPhone(id, newPhoneNumber);
        return updatedBranch != null ? ResponseEntity.ok(updatedBranch) : ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/holidays")
    @Operation(summary = "Agregar feriado", description = "Agrega un nuevo feriado a una sucursal")
    public ResponseEntity<Branch> addHoliday(
            @PathVariable String id,
            @RequestBody Branch.BranchHoliday holiday) {
        Branch branch = branchService.addHoliday(id, holiday);
        return branch != null ? ResponseEntity.ok(branch) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}/holidays")
    @Operation(summary = "Eliminar feriados", description = "Elimina todos los feriados de una sucursal")
    public ResponseEntity<Branch> removeHolidays(@PathVariable String id) {
        Branch branch = branchService.removeHolidays(id);
        return branch != null ? ResponseEntity.ok(branch) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/holidays")
    @Operation(summary = "Obtener feriados", description = "Obtiene todos los feriados de una sucursal")
    public ResponseEntity<List<Branch.BranchHoliday>> getBranchHolidays(@PathVariable String id) {
        List<Branch.BranchHoliday> holidays = branchService.getBranchHolidays(id);
        return holidays != null ? ResponseEntity.ok(holidays) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/holidays/check")
    @Operation(summary = "Verificar feriado", description = "Verifica si una fecha específica es feriado en una sucursal")
    public ResponseEntity<Boolean> isHoliday(
            @PathVariable String id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(branchService.isHoliday(id, date));
    }
} 