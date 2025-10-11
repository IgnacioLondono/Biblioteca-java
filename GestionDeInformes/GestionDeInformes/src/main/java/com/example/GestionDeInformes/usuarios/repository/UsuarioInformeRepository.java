package com.example.GestionDeInformes.usuarios.repository;

import com.example.GestionDeInformes.usuarios.model.UsuarioView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioInformeRepository extends JpaRepository<UsuarioView, Long> {
}

