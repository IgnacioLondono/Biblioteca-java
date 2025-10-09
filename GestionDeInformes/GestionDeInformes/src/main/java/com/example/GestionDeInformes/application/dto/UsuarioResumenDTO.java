package com.example.GestionDeInformes.application.dto;

public class UsuarioResumenDTO {
    private Long usuarioId;
    private String nombreCompleto;
    private String email;
    private long totalPrestamos;
    private long prestamosActivos;
    private long prestamosAtraso;
    private long multasPendientes;

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public long getTotalPrestamos() { return totalPrestamos; }
    public void setTotalPrestamos(long totalPrestamos) { this.totalPrestamos = totalPrestamos; }

    public long getPrestamosActivos() { return prestamosActivos; }
    public void setPrestamosActivos(long prestamosActivos) { this.prestamosActivos = prestamosActivos; }

    public long getPrestamosAtraso() { return prestamosAtraso; }
    public void setPrestamosAtraso(long prestamosAtraso) { this.prestamosAtraso = prestamosAtraso; }

    public long getMultasPendientes() { return multasPendientes; }
    public void setMultasPendientes(long multasPendientes) { this.multasPendientes = multasPendientes; }
}
