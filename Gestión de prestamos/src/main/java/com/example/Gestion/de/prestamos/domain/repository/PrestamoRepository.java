package com.example.Gestion.de.prestamos.domain.repository;

import com.example.Gestion.de.prestamos.domain.model.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {

    List<Prestamo> findByUsuarioId(Long usuarioId);

    boolean existsByEjemplarIdAndEstadoIn(Long ejemplarId, List<Prestamo.PrestamoEstado> estados);

    List<Prestamo> findByEstado(Prestamo.PrestamoEstado estado);

    List<Prestamo> findByEstadoNotAndFechaVencimientoBefore(Prestamo.PrestamoEstado estado, LocalDate fecha);
}
