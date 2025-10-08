package com.example.LibrosCatalogo.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta genérica para operaciones del API")
public class MensajeResponse {
    
    @Schema(description = "Mensaje descriptivo de la operación", example = "Libro creado exitosamente")
    private String mensaje;

    @Schema(description = "Datos adicionales de la respuesta")
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