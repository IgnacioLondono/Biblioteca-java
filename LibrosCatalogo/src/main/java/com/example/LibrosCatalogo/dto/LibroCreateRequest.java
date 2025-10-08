package com.example.LibrosCatalogo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Solicitud para crear un nuevo libro")
public class LibroCreateRequest {
    
    @Schema(description = "Título del libro", example = "Cien Años de Soledad", required = true)
    @NotBlank(message = "El título es obligatorio")
    private String titulo;

    @Schema(description = "ISBN del libro", example = "978-8437604947", required = true)
    @NotBlank(message = "El ISBN es obligatorio")
    private String isbn;

    @Schema(description = "Descripción del libro", example = "Una obra maestra de la literatura hispanoamericana")
    private String descripcion;

    @Schema(description = "Año de publicación", example = "1967")
    private Integer anioPublicacion;

    @Schema(description = "Editorial del libro", example = "Sudamericana")
    private String editorial;

    @Schema(description = "Idioma del libro", example = "Español")
    private String idioma;

    @Schema(description = "Número de páginas", example = "471")
    private Integer paginas;

    @Schema(description = "URL de la portada del libro", example = "https://images.example.com/cien-anios.jpg")
    private String portadaUrl;

    @Schema(description = "Cantidad total de ejemplares", example = "10", required = true)
    @NotNull(message = "La cantidad total es obligatoria")
    private Integer cantidadTotal;

    @Schema(description = "ID del autor", example = "1", required = true)
    @NotNull(message = "El autor es obligatorio")
    private Long autorId;

    @Schema(description = "ID de la categoría", example = "1", required = true)
    @NotNull(message = "La categoría es obligatoria")
    private Long categoriaId;

    // Getters y Setters (sin cambios)
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Integer getAnioPublicacion() { return anioPublicacion; }
    public void setAnioPublicacion(Integer anioPublicacion) { this.anioPublicacion = anioPublicacion; }

    public String getEditorial() { return editorial; }
    public void setEditorial(String editorial) { this.editorial = editorial; }

    public String getIdioma() { return idioma; }
    public void setIdioma(String idioma) { this.idioma = idioma; }

    public Integer getPaginas() { return paginas; }
    public void setPaginas(Integer paginas) { this.paginas = paginas; }

    public String getPortadaUrl() { return portadaUrl; }
    public void setPortadaUrl(String portadaUrl) { this.portadaUrl = portadaUrl; }

    public Integer getCantidadTotal() { return cantidadTotal; }
    public void setCantidadTotal(Integer cantidadTotal) { this.cantidadTotal = cantidadTotal; }

    public Long getAutorId() { return autorId; }
    public void setAutorId(Long autorId) { this.autorId = autorId; }

    public Long getCategoriaId() { return categoriaId; }
    public void setCategoriaId(Long categoriaId) { this.categoriaId = categoriaId; }
}