package com.example.Gestion.de.Usuarios.dto;

import java.util.List;

public class AuthResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String email;
    private String nombre;
    private String rut;
    private List<String> roles;

    // Constructores
    public AuthResponse() {}

    public AuthResponse(String token, String type, Long id, String email, String nombre, String rut, List<String> roles) {
        this.token = token;
        this.type = type;
        this.id = id;
        this.email = email;
        this.nombre = nombre;
        this.rut = rut;
        this.roles = roles;
    }

    // Builder pattern manual
    public static AuthResponseBuilder builder() {
        return new AuthResponseBuilder();
    }

    // Getters y Setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getRut() { return rut; }
    public void setRut(String rut) { this.rut = rut; }
    
    public List<String> getRoles() { return roles; }
    public void setRoles(List<String> roles) { this.roles = roles; }

    // Builder class
    public static class AuthResponseBuilder {
        private String token;
        private String type = "Bearer";
        private Long id;
        private String email;
        private String nombre;
        private String rut;
        private List<String> roles;

        public AuthResponseBuilder token(String token) {
            this.token = token;
            return this;
        }

        public AuthResponseBuilder type(String type) {
            this.type = type;
            return this;
        }

        public AuthResponseBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public AuthResponseBuilder email(String email) {
            this.email = email;
            return this;
        }

        public AuthResponseBuilder nombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public AuthResponseBuilder rut(String rut) {
            this.rut = rut;
            return this;
        }

        public AuthResponseBuilder roles(List<String> roles) {
            this.roles = roles;
            return this;
        }

        public AuthResponse build() {
            return new AuthResponse(token, type, id, email, nombre, rut, roles);
        }
    }
}