package com.examen.examen2p.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDate;

@Data
@Schema(description = "DTO para la información de un feriado")
public class BranchHolidayDTO {
    @Schema(description = "Fecha del feriado", example = "2024-01-01")
    private LocalDate date;

    @Schema(description = "Nombre o descripción del feriado", example = "Año Nuevo")
    private String name;
} 