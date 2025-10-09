package com.example.Notificaciones.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notificaciones")
public class Notificacion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long usuarioId;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false, length = 1000)
    private String mensaje;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoNotificacion tipo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoNotificacion estado;

    private String destinatarioEmail;
    private Integer intentosEnvio;
    private String errorMensaje;

    @Column(nullable = false)
    private LocalDateTime fechaCreacion;

    private LocalDateTime fechaEnvio;
    private LocalDateTime fechaLectura;

    // Constructores
    public Notificacion() {}

    public Notificacion(Long usuarioId, String titulo, String mensaje, TipoNotificacion tipo, String destinatarioEmail) {
        this.usuarioId = usuarioId;
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.tipo = tipo;
        this.destinatarioEmail = destinatarioEmail;
        this.estado = EstadoNotificacion.PENDIENTE;
        this.intentosEnvio = 0;
        this.fechaCreacion = LocalDateTime.now();
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public TipoNotificacion getTipo() { return tipo; }
    public void setTipo(TipoNotificacion tipo) { this.tipo = tipo; }

    public EstadoNotificacion getEstado() { return estado; }
    public void setEstado(EstadoNotificacion estado) { this.estado = estado; }

    public String getDestinatarioEmail() { return destinatarioEmail; }
    public void setDestinatarioEmail(String destinatarioEmail) { this.destinatarioEmail = destinatarioEmail; }

    public Integer getIntentosEnvio() { return intentosEnvio; }
    public void setIntentosEnvio(Integer intentosEnvio) { this.intentosEnvio = intentosEnvio; }

    public String getErrorMensaje() { return errorMensaje; }
    public void setErrorMensaje(String errorMensaje) { this.errorMensaje = errorMensaje; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public LocalDateTime getFechaEnvio() { return fechaEnvio; }
    public void setFechaEnvio(LocalDateTime fechaEnvio) { this.fechaEnvio = fechaEnvio; }

    public LocalDateTime getFechaLectura() { return fechaLectura; }
    public void setFechaLectura(LocalDateTime fechaLectura) { this.fechaLectura = fechaLectura; }

    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        if (estado == null) estado = EstadoNotificacion.PENDIENTE;
        if (intentosEnvio == null) intentosEnvio = 0;
    }
}