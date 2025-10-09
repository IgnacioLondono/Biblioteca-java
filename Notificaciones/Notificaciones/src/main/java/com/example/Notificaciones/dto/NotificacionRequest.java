package com.example.Notificaciones.dto;

import com.example.Notificaciones.model.TipoNotificacion;

public class NotificacionRequest {
    private Long usuarioId;
    private String titulo;
    private String mensaje;
    private TipoNotificacion tipo;
    private String destinatarioEmail;

    // Getters y Setters
    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public TipoNotificacion getTipo() { return tipo; }
    public void setTipo(TipoNotificacion tipo) { this.tipo = tipo; }

    public String getDestinatarioEmail() { return destinatarioEmail; }
    public void setDestinatarioEmail(String destinatarioEmail) { this.destinatarioEmail = destinatarioEmail; }
}