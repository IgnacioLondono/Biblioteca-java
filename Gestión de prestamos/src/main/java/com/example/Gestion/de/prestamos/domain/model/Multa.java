package com.example.Gestion.de.prestamos.domain.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Entidad que guarda las multas generadas por retrasos.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "multa")
public class Multa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "prestamo_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_multa_prestamo"))
    private Prestamo prestamo;

    @Column(name = "dias_mora", nullable = false)
    private Integer diasMora;

    @Column(name = "monto", nullable = false, precision = 12, scale = 2)
    private BigDecimal monto;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 20)
    private MultaEstado estado; // 'PENDIENTE', 'PAGADA', 'EXENTA'

    @Column(name = "motivo", length = 255)
    private String motivo;

    @CreationTimestamp
    @Column(name = "fecha_generacion", nullable = false, updatable = false)
    private Instant fechaGeneracion;

    @Column(name = "fecha_pago")
    private Instant fechaPago;

    public enum MultaEstado {
        PENDIENTE, PAGADA, EXENTA
    }
}
