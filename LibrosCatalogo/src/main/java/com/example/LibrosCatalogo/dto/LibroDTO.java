package com.example.LibrosCatalogo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "DTO que representa un libro en el sistema")
public class LibroDTO {
    
    @Schema(description = "ID único del libro", example = "1")
    private Long id;

    @Schema(description = "Título del libro", example = "Cien Años de Soledad", required = true)
    private String titulo;

    @Schema(description = "ISBN del libro", example = "978-8437604947", required = true)
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

    @Schema(description = "Cantidad total de ejemplares", example = "10")
    private Integer cantidadTotal;

    @Schema(description = "Cantidad de ejemplares disponibles", example = "8")
    private Integer cantidadDisponible;

    @Schema(description = "Indica si el libro está activo", example = "true")
    private Boolean activo;

    @Schema(description = "Fecha de creación del registro")
    private LocalDateTime fechaCreacion;

    @Schema(description = "Información del autor")
    private AutorDTO autor;

    @Schema(description = "Información de la categoría")
    private CategoriaDTO categoria;

    // Constructores
    public LibroDTO() {}

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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

    public Integer getCantidadDisponible() { return cantidadDisponible; }
    public void setCantidadDisponible(Integer cantidadDisponible) { this.cantidadDisponible = cantidadDisponible; }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public AutorDTO getAutor() { return autor; }
    public void setAutor(AutorDTO autor) { this.autor = autor; }

    public CategoriaDTO getCategoria() { return categoria; }
    public void setCategoria(CategoriaDTO categoria) { this.categoria = categoria; }

    @Schema(description = "DTO que representa un autor")
    public static class AutorDTO {
        @Schema(description = "ID único del autor", example = "1")
        private Long id;

        @Schema(description = "Nombre del autor", example = "Gabriel")
        private String nombre;

        @Schema(description = "Apellido del autor", example = "García Márquez")
        private String apellido;

        @Schema(description = "Nacionalidad del autor", example = "Colombiano")
        private String nacionalidad;

        // Getters y Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }

        public String getApellido() { return apellido; }
        public void setApellido(String apellido) { this.apellido = apellido; }

        public String getNacionalidad() { return nacionalidad; }
        public void setNacionalidad(String nacionalidad) { this.nacionalidad = nacionalidad; }
    }

    @Schema(description = "DTO que representa una categoría")
    public static class CategoriaDTO {
        @Schema(description = "ID único de la categoría", example = "1")
        private Long id;

        @Schema(description = "Nombre de la categoría", example = "Ficción")
        private String nombre;

        @Schema(description = "Descripción de la categoría", example = "Novelas de ficción y literatura imaginativa")
        private String descripcion;

        // Getters y Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }

        public String getDescripcion() { return descripcion; }
        public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    }
}
