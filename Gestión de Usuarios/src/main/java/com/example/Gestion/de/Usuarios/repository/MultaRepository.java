package com.example.Gestion.de.Usuarios.repository;

import com.example.Gestion.de.Usuarios.model.Multa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MultaRepository extends JpaRepository<Multa, Long> {
    List<Multa> findByUsuarioIdAndPagadaFalse(Long usuarioId);
    List<Multa> findByUsuarioId(Long usuarioId);
    List<Multa> findByPagadaFalse();
}