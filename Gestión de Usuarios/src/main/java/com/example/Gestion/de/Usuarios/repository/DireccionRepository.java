package com.example.Gestion.de.Usuarios.repository;

import com.example.Gestion.de.Usuarios.model.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DireccionRepository extends JpaRepository<Direccion, Long> {
}