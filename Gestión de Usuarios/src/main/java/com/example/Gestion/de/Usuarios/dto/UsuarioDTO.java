package com.example.Gestion.de.Usuarios.dto;

import java.time.LocalDateTime;
import java.util.List;

public class UsuarioDTO {
    private Long id;
    private String rut;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private DireccionDTO direccion;
    private List<String> roles;
    private Boolean activo;
    private LocalDateTime fechaCreacion;

    public UsuarioDTO() {}

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getRut() { return rut; }
    public void setRut(String rut) { this.rut = rut; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public DireccionDTO getDireccion() { return direccion; }
    public void setDireccion(DireccionDTO direccion) { this.direccion = direccion; }

    public List<String> getRoles() { return roles; }
    public void setRoles(List<String> roles) { this.roles = roles; }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public static class DireccionDTO {
        private String calle;
        private String numero;
        private String departamento;
        private String codigoPostal;
        private String comuna;
        private String region;

        public DireccionDTO() {}

        // Getters y Setters
        public String getCalle() { return calle; }
        public void setCalle(String calle) { this.calle = calle; }

        public String getNumero() { return numero; }
        public void setNumero(String numero) { this.numero = numero; }

        public String getDepartamento() { return departamento; }
        public void setDepartamento(String departamento) { this.departamento = departamento; }

        public String getCodigoPostal() { return codigoPostal; }
        public void setCodigoPostal(String codigoPostal) { this.codigoPostal = codigoPostal; }

        public String getComuna() { return comuna; }
        public void setComuna(String comuna) { this.comuna = comuna; }

        public String getRegion() { return region; }
        public void setRegion(String region) { this.region = region; }
    }
}