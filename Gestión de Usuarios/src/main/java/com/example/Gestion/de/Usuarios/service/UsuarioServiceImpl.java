package com.example.Gestion.de.Usuarios.service;

import com.example.Gestion.de.Usuarios.dto.UsuarioCreateRequest;
import com.example.Gestion.de.Usuarios.dto.UsuarioDTO;
import com.example.Gestion.de.Usuarios.model.*;
import com.example.Gestion.de.Usuarios.repository.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final ComunaRepository comunaRepository;
    private final DireccionRepository direccionRepository;
    private final PasswordEncoder passwordEncoder;

    // Constructor manual SIN Lombok
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, 
                             RolRepository rolRepository,
                             ComunaRepository comunaRepository,
                             DireccionRepository direccionRepository,
                             PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.comunaRepository = comunaRepository;
        this.direccionRepository = direccionRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Los métodos de implementación se mantienen igual...
    @Override
    @Transactional
    public UsuarioDTO crearUsuario(UsuarioCreateRequest request) {
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }
        
        if (usuarioRepository.existsByRut(request.getRut())) {
            throw new RuntimeException("El RUT ya está registrado");
        }

        Direccion direccion = crearDireccion(request.getDireccion());

        Rol rolUsuario = rolRepository.findByNombre("USUARIO")
                .orElseThrow(() -> new RuntimeException("Rol USUARIO no encontrado"));

        Usuario usuario = new Usuario();
        usuario.setRut(request.getRut());
        usuario.setNombre(request.getNombre());
        usuario.setApellido(request.getApellido());
        usuario.setEmail(request.getEmail());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setTelefono(request.getTelefono());
        usuario.setDireccion(direccion);
        usuario.setRoles(Collections.singletonList(rolUsuario));
        usuario.setActivo(true);

        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        return convertirADTO(usuarioGuardado);
    }

    // ... resto de los métodos se mantienen igual (sin cambios)
    // Solo asegúrate de que todos los getters en las entidades sean manuales

    private UsuarioDTO convertirADTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        
        dto.setId(usuario.getId());
        dto.setRut(usuario.getRut());
        dto.setNombre(usuario.getNombre());
        dto.setApellido(usuario.getApellido());
        dto.setEmail(usuario.getEmail());
        dto.setTelefono(usuario.getTelefono());
        dto.setActivo(usuario.getActivo());
        dto.setFechaCreacion(usuario.getFechaCreacion());

        if (usuario.getRoles() != null) {
            List<String> roles = usuario.getRoles().stream()
                    .map(rol -> rol != null ? rol.getNombre() : "")
                    .filter(nombre -> !nombre.isEmpty())
                    .collect(Collectors.toList());
            dto.setRoles(roles);
        }

        if (usuario.getDireccion() != null) {
            UsuarioDTO.DireccionDTO direccionDTO = new UsuarioDTO.DireccionDTO();
            direccionDTO.setCalle(usuario.getDireccion().getCalle());
            direccionDTO.setNumero(usuario.getDireccion().getNumero());
            direccionDTO.setDepartamento(usuario.getDireccion().getDepartamento());
            direccionDTO.setCodigoPostal(usuario.getDireccion().getCodigoPostal());
            
            if (usuario.getDireccion().getComuna() != null) {
                direccionDTO.setComuna(usuario.getDireccion().getComuna().getNombre());
                
                if (usuario.getDireccion().getComuna().getRegion() != null) {
                    direccionDTO.setRegion(usuario.getDireccion().getComuna().getRegion().getNombre());
                }
            }
            
            dto.setDireccion(direccionDTO);
        }

        return dto;
    }

    // ... resto de los métodos
}