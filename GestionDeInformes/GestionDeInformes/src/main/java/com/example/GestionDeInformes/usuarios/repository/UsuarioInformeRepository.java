package com.example.GestionDeInformes.usuarios.repository;

import com.example.GestionDeInformes.usuarios.model.UsuarioView;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioInformeRepository extends JpaRepository<UsuarioView, Long> {
}
