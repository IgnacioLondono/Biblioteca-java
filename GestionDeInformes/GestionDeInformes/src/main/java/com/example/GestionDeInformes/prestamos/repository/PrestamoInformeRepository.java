package com.example.GestionDeInformes.prestamos.repository;

import com.example.GestionDeInformes.prestamos.model.PrestamoView;
import com.example.GestionDeInformes.prestamos.model.PrestamoView.PrestamoEstado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrestamoInformeRepository extends JpaRepository<PrestamoView, Long> {
    long countByUsuarioId(Long usuarioId);
    long countByUsuarioIdAndEstado(Long usuarioId, PrestamoEstado estado);
    long countByEstado(PrestamoEstado estado);
}
