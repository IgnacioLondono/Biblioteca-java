package com.example.Gestion.de.Usuarios.repository;

import com.example.Gestion.de.Usuarios.model.Comuna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ComunaRepository extends JpaRepository<Comuna, Long> {
    Optional<Comuna> findByNombre(String nombre);
    Optional<Comuna> findByCodigo(String codigo);
}