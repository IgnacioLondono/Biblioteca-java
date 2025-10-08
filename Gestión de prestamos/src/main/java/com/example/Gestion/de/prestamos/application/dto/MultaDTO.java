package com.example.Gestion.de.prestamos.application.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MultaDTO {
    private Long id;

    @NotNull
    private Long prestamoId;

    @NotNull
    @Min(0)
    private Integer diasMora;

    @NotNull
    @DecimalMin(value = "0.00")
    private BigDecimal monto;

    @NotBlank
    private String estado;

    private String motivo;

    private Instant fechaGeneracion;

    private Instant fechaPago;
}
