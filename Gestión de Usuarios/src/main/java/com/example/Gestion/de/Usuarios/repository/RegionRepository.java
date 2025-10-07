package com.example.Gestion.de.Usuarios.repository;

import com.example.Gestion.de.Usuarios.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {
    Optional<Region> findByNombre(String nombre);
    Optional<Region> findByCodigo(String codigo);
}