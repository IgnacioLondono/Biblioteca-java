package com.example.Notificaciones.dto;

public class MensajeResponse {
    private String mensaje;
    private Object data;

    public MensajeResponse(String mensaje) {
        this.mensaje = mensaje;
    }

    public MensajeResponse(String mensaje, Object data) {
        this.mensaje = mensaje;
        this.data = data;
    }

    // Getters y Setters
    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public Object getData() { return data; }
    public void setData(Object data) { this.data = data; }
}