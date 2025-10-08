package com.example.Gestion.de.prestamos.domain.repository;

import com.example.Gestion.de.prestamos.domain.model.Multa;
import com.example.Gestion.de.prestamos.domain.model.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MultaRepository extends JpaRepository<Multa, Long> {
    List<Multa> findByPrestamo(Prestamo prestamo);
}
