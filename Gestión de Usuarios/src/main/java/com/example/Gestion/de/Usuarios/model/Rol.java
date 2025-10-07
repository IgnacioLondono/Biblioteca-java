package com.example.Gestion.de.Usuarios.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.List;

@Entity
@Table(name = "roles")
public class Rol {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del rol es obligatorio")
    @Column(unique = true)
    private String nombre;

    private String descripcion;

    @ManyToMany(mappedBy = "roles")
    private List<Usuario> usuarios;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "rol_permisos", joinColumns = @JoinColumn(name = "rol_id"))
    @Column(name = "permiso")
    private List<String> permisos;

    // Constructores
    public Rol() {}

    public Rol(Long id, String nombre, String descripcion, List<Usuario> usuarios, List<String> permisos) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.usuarios = usuarios;
        this.permisos = permisos;
    }

    // GETTERS MANUALES
    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public List<Usuario> getUsuarios() { return usuarios; }
    public List<String> getPermisos() { return permisos; }

    // SETTERS
    public void setId(Long id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setUsuarios(List<Usuario> usuarios) { this.usuarios = usuarios; }
    public void setPermisos(List<String> permisos) { this.permisos = permisos; }
}