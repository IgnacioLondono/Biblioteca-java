package com.example.Gestion.de.Usuarios.service;

import com.example.Gestion.de.Usuarios.dto.UsuarioCreateRequest;
import com.example.Gestion.de.Usuarios.dto.UsuarioDTO;
import com.example.Gestion.de.Usuarios.model.Usuario;

import java.util.List;

public interface UsuarioService {
    UsuarioDTO crearUsuario(UsuarioCreateRequest request);
    UsuarioDTO actualizarUsuario(Long id, UsuarioCreateRequest request);
    void eliminarUsuario(Long id);
    UsuarioDTO obtenerUsuarioPorId(Long id);
    List<UsuarioDTO> obtenerTodosLosUsuarios();
    UsuarioDTO obtenerUsuarioPorEmail(String email);
    UsuarioDTO obtenerUsuarioPorRut(String rut);
    List<UsuarioDTO> obtenerUsuariosPorRol(String rol);
    void activarUsuario(Long id);
    void desactivarUsuario(Long id);
    Usuario getUsuarioEntity(Long id);
}