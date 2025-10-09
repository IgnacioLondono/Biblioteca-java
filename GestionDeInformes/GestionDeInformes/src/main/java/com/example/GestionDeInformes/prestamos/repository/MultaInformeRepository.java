package com.example.GestionDeInformes.prestamos.repository;

import com.example.GestionDeInformes.prestamos.model.MultaView;
import com.example.GestionDeInformes.prestamos.model.MultaView.MultaEstado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MultaInformeRepository extends JpaRepository<MultaView, Long> {
    long countByPrestamo_UsuarioIdAndEstado(Long usuarioId, MultaEstado estado);
    long countByEstado(MultaEstado estado);
}
