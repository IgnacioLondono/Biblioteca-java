package com.example.Gestion.de.Usuarios.model;

import lombok.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.List;

@Entity
@Table(name = "comunas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comuna {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre de la comuna es obligatorio")
    @Column(unique = true)
    private String nombre;

    @NotBlank(message = "El código de comuna es obligatorio")
    @Column(unique = true)
    private String codigo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private Region region;

    @OneToMany(mappedBy = "comuna", cascade = CascadeType.ALL)
    private List<Direccion> direcciones;

public Long getId() { return id; }
public String getNombre() { return nombre; }
public String getCodigo() { return codigo; }
public Region getRegion() { return region; }
public List<Direccion> getDirecciones() { return direcciones; }

}