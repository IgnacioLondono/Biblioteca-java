package com.example.LibrosCatalogo.repository;

import com.example.LibrosCatalogo.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
    
    Optional<Libro> findByIsbn(String isbn);
    List<Libro> findByTituloContainingIgnoreCase(String titulo);
    List<Libro> findByAutorNombreContainingIgnoreCase(String autorNombre);
    List<Libro> findByCategoriaNombre(String categoriaNombre);
    List<Libro> findByActivoTrue();
    List<Libro> findByCantidadDisponibleGreaterThan(Integer cantidad);
    
    @Query("SELECT l FROM Libro l WHERE " +
           "LOWER(l.titulo) LIKE LOWER(CONCAT('%', :termino, '%')) OR " +
           "LOWER(l.autor.nombre) LIKE LOWER(CONCAT('%', :termino, '%')) OR " +
           "LOWER(l.autor.apellido) LIKE LOWER(CONCAT('%', :termino, '%')) OR " +
           "LOWER(l.editorial) LIKE LOWER(CONCAT('%', :termino, '%'))")
    List<Libro> buscarPorTermino(@Param("termino") String termino);
    
    boolean existsByIsbn(String isbn);
}