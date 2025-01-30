package com.examen.examen2p.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO para la creación de una nueva sucursal")
public class BranchRequestDTO {
    @Schema(description = "Dirección de correo electrónico de la sucursal", example = "sucursal.quito@banquito.com")
    private String emailAddress;

    @Schema(description = "Nombre de la sucursal", example = "Sucursal Quito Norte")
    private String name;

    @Schema(description = "Número de teléfono de la sucursal", example = "023456789")
    private String phoneNumber;

    @Schema(description = "Estado de la sucursal", example = "ACTIVO")
    private String state;
} 