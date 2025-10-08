package com.example.LibrosCatalogo.repository;

import com.example.LibrosCatalogo.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {
    
    Optional<Autor> findByNombreAndApellido(String nombre, String apellido);
    List<Autor> findByNombreContainingIgnoreCase(String nombre);
    List<Autor> findByActivoTrue();
    boolean existsByNombreAndApellido(String nombre, String apellido);
}