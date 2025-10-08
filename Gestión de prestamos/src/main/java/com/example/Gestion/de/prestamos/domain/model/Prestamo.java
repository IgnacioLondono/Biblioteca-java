package com.example.Gestion.de.prestamos.domain.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Check;

import java.time.Instant;
import java.time.LocalDate;

/**
 * Entidad que representa un préstamo realizado a un usuario.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "prestamo",
        indexes = {
                @Index(name = "idx_prestamo_usuario", columnList = "usuario_id"),
                @Index(name = "idx_prestamo_ejemplar", columnList = "ejemplar_id"),
                @Index(name = "idx_prestamo_estado", columnList = "estado")
        })
@Check(constraints = "estado IN ('ACTIVO','DEVUELTO','ATRASO','CANCELADO','PERDIDO')")
public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId; // ID del usuario (ms-usuarios)

    @Column(name = "ejemplar_id", nullable = false)
    private Long ejemplarId; // ID del ejemplar/libro (ms-libros)

    @Column(name = "fecha_prestamo", nullable = false)
    private LocalDate fechaPrestamo; // Fecha en que se realizó el préstamo

    @Column(name = "fecha_vencimiento", nullable = false)
    private LocalDate fechaVencimiento; // Fecha límite de devolución

    @Column(name = "fecha_devolucion")
    private LocalDate fechaDevolucion; // Fecha real de devolución (si aplica)

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 20)
    private PrestamoEstado estado; // Estado actual del préstamo

    @Column(name = "renovaciones", nullable = false)
    private Integer renovaciones;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

    public enum PrestamoEstado {
        ACTIVO, DEVUELTO, ATRASO, CANCELADO, PERDIDO
    }
}
