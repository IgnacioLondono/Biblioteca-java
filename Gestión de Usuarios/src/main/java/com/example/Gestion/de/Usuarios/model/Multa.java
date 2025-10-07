package com.example.Gestion.de.Usuarios.model;

import lombok.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "multas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Multa {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto debe ser positivo")
    private BigDecimal monto;

    private String motivo;

    private LocalDateTime fechaImposicion;

    private LocalDateTime fechaVencimiento;

    private Boolean pagada;

    @PrePersist
    protected void onCreate() {
        fechaImposicion = LocalDateTime.now();
        pagada = false;
    }
}