package com.example.Gestion.de.Usuarios.dto;

public class UsuarioCreateRequest {
    private String rut;
    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private String telefono;
    private DireccionRequest direccion;

    public UsuarioCreateRequest() {}

    // Getters y Setters
    public String getRut() { return rut; }
    public void setRut(String rut) { this.rut = rut; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public DireccionRequest getDireccion() { return direccion; }
    public void setDireccion(DireccionRequest direccion) { this.direccion = direccion; }

    public static class DireccionRequest {
        private String calle;
        private String numero;
        private String departamento;
        private String codigoPostal;
        private String comunaNombre;

        public DireccionRequest() {}

        // Getters y Setters
        public String getCalle() { return calle; }
        public void setCalle(String calle) { this.calle = calle; }

        public String getNumero() { return numero; }
        public void setNumero(String numero) { this.numero = numero; }

        public String getDepartamento() { return departamento; }
        public void setDepartamento(String departamento) { this.departamento = departamento; }

        public String getCodigoPostal() { return codigoPostal; }
        public void setCodigoPostal(String codigoPostal) { this.codigoPostal = codigoPostal; }

        public String getComunaNombre() { return comunaNombre; }
        public void setComunaNombre(String comunaNombre) { this.comunaNombre = comunaNombre; }
    }
}