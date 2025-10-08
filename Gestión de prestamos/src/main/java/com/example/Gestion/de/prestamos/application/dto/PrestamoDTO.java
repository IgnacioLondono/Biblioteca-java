package com.example.Gestion.de.prestamos.application.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrestamoDTO {
    private Long id;

    @NotNull
    private Long usuarioId;

    @NotNull
    private Long ejemplarId;

    private LocalDate fechaPrestamo;

    private LocalDate fechaVencimiento;

    private LocalDate fechaDevolucion;

    private String estado;

    @Min(0)
    private Integer renovaciones;
}
