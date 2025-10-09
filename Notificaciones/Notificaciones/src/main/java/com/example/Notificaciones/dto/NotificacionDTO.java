package com.example.Notificaciones.dto;

import com.example.Notificaciones.model.EstadoNotificacion;
import com.example.Notificaciones.model.TipoNotificacion;
import java.time.LocalDateTime;

public class NotificacionDTO {
    private Long id;
    private Long usuarioId;
    private String titulo;
    private String mensaje;
    private TipoNotificacion tipo;
    private EstadoNotificacion estado;
    private String destinatarioEmail;
    private Integer intentosEnvio;
    private String errorMensaje;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaEnvio;
    private LocalDateTime fechaLectura;

    // Constructores
    public NotificacionDTO() {}

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
}