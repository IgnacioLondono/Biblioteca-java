package com.example.LibrosCatalogo.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Solicitud para búsqueda de libros con filtros")
public class BusquedaRequest {
    
    @Schema(description = "Término de búsqueda (busca en título, autor, editorial)", example = "García Márquez")
    private String termino;

    @Schema(description = "Filtrar por categoría", example = "Ficción")
    private String categoria;

    @Schema(description = "Filtrar por autor", example = "Gabriel García Márquez")
    private String autor;

    @Schema(description = "Mostrar solo libros disponibles", example = "true")
    private Boolean soloDisponibles;

    // Getters y Setters (sin cambios)
    public String getTermino() { return termino; }
    public void setTermino(String termino) { this.termino = termino; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public Boolean getSoloDisponibles() { return soloDisponibles; }
    public void setSoloDisponibles(Boolean soloDisponibles) { this.soloDisponibles = soloDisponibles; }
}