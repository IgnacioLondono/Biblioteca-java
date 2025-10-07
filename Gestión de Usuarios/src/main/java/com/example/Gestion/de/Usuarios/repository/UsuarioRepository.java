package com.example.Gestion.de.Usuarios.repository;

import com.example.Gestion.de.Usuarios.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByRut(String rut);
    List<Usuario> findByActivoTrue();
    List<Usuario> findByRolesNombre(String rolNombre);
    
    @Query("SELECT u FROM Usuario u WHERE u.email = :email AND u.activo = true")
    Optional<Usuario> findActiveByEmail(@Param("email") String email);
    
    boolean existsByEmail(String email);
    boolean existsByRut(String rut);
}