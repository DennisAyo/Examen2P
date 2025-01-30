package com.examen.examen2p.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Schema(description = "DTO para la respuesta con información de una sucursal")
public class BranchResponseDTO {
    @Schema(description = "Identificador único de la sucursal", example = "60c73b4e6c2d8a3e5c1d8b4a")
    private String id;

    @Schema(description = "Dirección de correo electrónico de la sucursal", example = "sucursal.quito@banquito.com")
    private String emailAddress;

    @Schema(description = "Nombre de la sucursal", example = "Sucursal Quito Norte")
    private String name;

    @Schema(description = "Número de teléfono de la sucursal", example = "023456789")
    private String phoneNumber;

    @Schema(description = "Estado de la sucursal", example = "ACTIVO")
    private String state;

    @Schema(description = "Fecha y hora de creación de la sucursal")
    private LocalDateTime creationDate;

    @Schema(description = "Fecha y hora de la última modificación de la sucursal")
    private LocalDateTime lastModifiedDate;

    @Schema(description = "Lista de feriados de la sucursal")
    private List<BranchHolidayDTO> branchHolidays;
} 