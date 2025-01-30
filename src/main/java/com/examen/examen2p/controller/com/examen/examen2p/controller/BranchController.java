package com.examen.examen2p.controller;

import com.examen.examen2p.dto.BranchRequestDTO;
import com.examen.examen2p.dto.BranchResponseDTO;
import com.examen.examen2p.dto.BranchHolidayDTO;
import com.examen.examen2p.service.BranchService;
import com.examen.examen2p.mapper.BranchMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/branches")
@Tag(name = "Sucursales", description = "API para la gestión de sucursales bancarias y sus feriados")
public class BranchController {

    @Autowired
    private BranchService branchService;

    @Autowired
    private BranchMapper branchMapper;

    @Operation(
        summary = "Obtener todas las sucursales",
        description = "Retorna un listado completo de todas las sucursales bancarias registradas."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de sucursales obtenida exitosamente",
                content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = BranchResponseDTO.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<BranchResponseDTO>> getAllBranches() {
        return ResponseEntity.ok(branchService.getAllBranches().stream()
                .map(branchMapper::toDTO)
                .toList());
    }

    @Operation(
        summary = "Crear una nueva sucursal",
        description = "Crea una nueva sucursal bancaria sin feriados iniciales.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos necesarios para crear una sucursal",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = BranchRequestDTO.class)
            )
        )
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sucursal creada exitosamente",
                content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = BranchResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud", content = @Content)
    })
    @PostMapping
    public ResponseEntity<BranchResponseDTO> createBranch(@RequestBody BranchRequestDTO branchDTO) {
        return ResponseEntity.ok(branchMapper.toDTO(
                branchService.createBranch(branchMapper.toEntity(branchDTO))));
    }

    @Operation(
        summary = "Obtener sucursal por ID",
        description = "Busca y retorna una sucursal específica por su ID."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sucursal encontrada exitosamente",
                content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = BranchResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Sucursal no encontrada", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<BranchResponseDTO> getBranchById(
            @Parameter(description = "ID de la sucursal a buscar", required = true)
            @PathVariable String id) {
        return branchService.getBranchById(id)
                .map(branch -> ResponseEntity.ok(branchMapper.toDTO(branch)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
        summary = "Actualizar teléfono de sucursal",
        description = "Actualiza el número de teléfono de una sucursal específica."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Teléfono actualizado exitosamente",
                content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = BranchResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Sucursal no encontrada", content = @Content)
    })
    @PatchMapping("/{id}/phone")
    public ResponseEntity<BranchResponseDTO> updateBranchPhone(
            @Parameter(description = "ID de la sucursal", required = true) @PathVariable String id,
            @Parameter(description = "Nuevo número de teléfono", required = true) @RequestParam String newPhoneNumber) {
        return ResponseEntity.ok(branchMapper.toDTO(
                branchService.updateBranchPhone(id, newPhoneNumber)));
    }

    @Operation(
        summary = "Agregar feriado",
        description = "Agrega un nuevo feriado a una sucursal específica.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos del feriado a agregar",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = BranchHolidayDTO.class)
            )
        )
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Feriado agregado exitosamente",
                content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = BranchResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Sucursal no encontrada", content = @Content)
    })
    @PostMapping("/{id}/holidays")
    public ResponseEntity<BranchResponseDTO> addHoliday(
            @Parameter(description = "ID de la sucursal", required = true) @PathVariable String id,
            @RequestBody BranchHolidayDTO holidayDTO) {
        return ResponseEntity.ok(branchMapper.toDTO(
                branchService.addHoliday(id, branchMapper.toHolidayEntity(holidayDTO))));
    }

    @Operation(
        summary = "Eliminar feriados",
        description = "Elimina todos los feriados de una sucursal específica."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Feriados eliminados exitosamente",
                content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = BranchResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Sucursal no encontrada", content = @Content)
    })
    @DeleteMapping("/{id}/holidays")
    public ResponseEntity<BranchResponseDTO> removeHolidays(
            @Parameter(description = "ID de la sucursal", required = true) @PathVariable String id) {
        return ResponseEntity.ok(branchMapper.toDTO(
                branchService.removeHolidays(id)));
    }

    @Operation(
        summary = "Obtener feriados",
        description = "Obtiene todos los feriados de una sucursal específica."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de feriados obtenida exitosamente",
                content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = BranchHolidayDTO.class))),
        @ApiResponse(responseCode = "404", description = "Sucursal no encontrada", content = @Content)
    })
    @GetMapping("/{id}/holidays")
    public ResponseEntity<List<BranchHolidayDTO>> getBranchHolidays(
            @Parameter(description = "ID de la sucursal", required = true) @PathVariable String id) {
        List<BranchHolidayDTO> holidays = branchService.getBranchHolidays(id).stream()
                .map(branchMapper::toHolidayDTO)
                .toList();
        return ResponseEntity.ok(holidays);
    }

    @Operation(
        summary = "Verificar feriado",
        description = "Verifica si una fecha específica es feriado en una sucursal."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Verificación realizada exitosamente",
                content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Boolean.class))),
        @ApiResponse(responseCode = "404", description = "Sucursal no encontrada", content = @Content)
    })
    @GetMapping("/{id}/holidays/check")
    public ResponseEntity<Boolean> isHoliday(
            @Parameter(description = "ID de la sucursal", required = true) @PathVariable String id,
            @Parameter(description = "Fecha a verificar (formato: YYYY-MM-DD)", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(branchService.isHoliday(id, date));
    }
} 