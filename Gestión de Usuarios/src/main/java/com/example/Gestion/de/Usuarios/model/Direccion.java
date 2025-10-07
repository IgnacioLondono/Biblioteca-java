package com.example.Gestion.de.Usuarios.model;

import lombok.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "direcciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Direccion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "La calle es obligatoria")
    private String calle;

    @NotBlank(message = "El número es obligatorio")
    private String numero;

    private String departamento;

    @NotBlank(message = "El código postal es obligatorio")
    private String codigoPostal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comuna_id")
    private Comuna comuna;

    @OneToOne(mappedBy = "direccion")
    private Usuario usuario;

public Long getId() { return id; }
public String getCalle() { return calle; }
public String getNumero() { return numero; }
public String getDepartamento() { return departamento; }
public String getCodigoPostal() { return codigoPostal; }
public Comuna getComuna() { return comuna; }
public Usuario getUsuario() { return usuario; }




}