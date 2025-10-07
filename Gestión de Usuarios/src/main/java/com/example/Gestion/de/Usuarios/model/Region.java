package com.example.Gestion.de.Usuarios.model;

import lombok.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.List;

@Entity
@Table(name = "regiones")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Region {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre de la región es obligatorio")
    @Column(unique = true)
    private String nombre;

    @NotBlank(message = "El código de región es obligatorio")
    @Column(unique = true)
    private String codigo;

    @NotBlank(message = "La región numérica es obligatoria")
    @Column(unique = true)
    private String regionNumerica;

    @OneToMany(mappedBy = "region", cascade = CascadeType.ALL)
    private List<Comuna> comunas;

    public Long getId() { return id; }
public String getNombre() { return nombre; }
public String getCodigo() { return codigo; }
public String getRegionNumerica() { return regionNumerica; }
public List<Comuna> getComunas() { return comunas; }


}