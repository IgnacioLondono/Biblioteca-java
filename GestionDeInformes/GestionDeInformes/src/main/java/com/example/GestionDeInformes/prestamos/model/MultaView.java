package com.example.GestionDeInformes.prestamos.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "multa")
public class MultaView {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "prestamo_id", nullable = false)
    private PrestamoView prestamo;

    @Column(name = "dias_mora", nullable = false)
    private Integer diasMora;

    @Column(name = "monto", nullable = false, precision = 12, scale = 2)
    private BigDecimal monto;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 20)
    private MultaEstado estado;

    @Column(name = "motivo", length = 255)
    private String motivo;

    @Column(name = "fecha_generacion", nullable = false, updatable = false)
    private Instant fechaGeneracion;

    @Column(name = "fecha_pago")
    private Instant fechaPago;

    public enum MultaEstado {
        PENDIENTE, PAGADA, EXENTA
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public PrestamoView getPrestamo() { return prestamo; }
    public void setPrestamo(PrestamoView prestamo) { this.prestamo = prestamo; }

    public Integer getDiasMora() { return diasMora; }
    public void setDiasMora(Integer diasMora) { this.diasMora = diasMora; }

    public BigDecimal getMonto() { return monto; }
    public void setMonto(BigDecimal monto) { this.monto = monto; }

    public MultaEstado getEstado() { return estado; }
    public void setEstado(MultaEstado estado) { this.estado = estado; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }

    public Instant getFechaGeneracion() { return fechaGeneracion; }
    public void setFechaGeneracion(Instant fechaGeneracion) { this.fechaGeneracion = fechaGeneracion; }

    public Instant getFechaPago() { return fechaPago; }
    public void setFechaPago(Instant fechaPago) { this.fechaPago = fechaPago; }
}
