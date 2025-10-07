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

    private Direccion crearDireccion(UsuarioCreateRequest.DireccionRequest direccionReq) {
        if (direccionReq == null) {
            return null;
        }
        Comuna comuna = null;
        if (direccionReq.getComunaNombre() != null && !direccionReq.getComunaNombre().isBlank()) {
            comuna = comunaRepository.findByNombre(direccionReq.getComunaNombre())
                    .orElseThrow(() -> new RuntimeException("Comuna no encontrada: " + direccionReq.getComunaNombre()));
        }

        Direccion direccion = new Direccion();
        direccion.setCalle(direccionReq.getCalle());
        direccion.setNumero(direccionReq.getNumero());
        direccion.setDepartamento(direccionReq.getDepartamento());
        direccion.setCodigoPostal(direccionReq.getCodigoPostal());
        direccion.setComuna(comuna);

        return direccionRepository.save(direccion);
    }

    @Override
    @Transactional
    public UsuarioDTO actualizarUsuario(Long id, UsuarioCreateRequest request) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (request.getEmail() != null && !request.getEmail().equals(usuario.getEmail())
                && usuarioRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }

        if (request.getRut() != null && !request.getRut().equals(usuario.getRut())
                && usuarioRepository.existsByRut(request.getRut())) {
            throw new RuntimeException("El RUT ya está registrado");
        }

        if (request.getRut() != null) usuario.setRut(request.getRut());
        if (request.getNombre() != null) usuario.setNombre(request.getNombre());
        if (request.getApellido() != null) usuario.setApellido(request.getApellido());
        if (request.getEmail() != null) usuario.setEmail(request.getEmail());
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        if (request.getTelefono() != null) usuario.setTelefono(request.getTelefono());

        if (request.getDireccion() != null) {
            Direccion dir = usuario.getDireccion();
            if (dir == null) {
                dir = crearDireccion(request.getDireccion());
                usuario.setDireccion(dir);
            } else {
                UsuarioCreateRequest.DireccionRequest dr = request.getDireccion();
                if (dr.getCalle() != null) dir.setCalle(dr.getCalle());
                if (dr.getNumero() != null) dir.setNumero(dr.getNumero());
                if (dr.getDepartamento() != null) dir.setDepartamento(dr.getDepartamento());
                if (dr.getCodigoPostal() != null) dir.setCodigoPostal(dr.getCodigoPostal());
                if (dr.getComunaNombre() != null && !dr.getComunaNombre().isBlank()) {
                    Comuna comuna = comunaRepository.findByNombre(dr.getComunaNombre())
                            .orElseThrow(() -> new RuntimeException("Comuna no encontrada: " + dr.getComunaNombre()));
                    dir.setComuna(comuna);
                }
                direccionRepository.save(dir);
            }
        }

        Usuario actualizado = usuarioRepository.save(usuario);
        return convertirADTO(actualizado);
    }

    @Override
    @Transactional
    public void eliminarUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuario.setActivo(false);
        usuarioRepository.save(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioDTO obtenerUsuarioPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return convertirADTO(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioDTO> obtenerTodosLosUsuarios() {
        return usuarioRepository.findByActivoTrue()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioDTO obtenerUsuarioPorEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return convertirADTO(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioDTO obtenerUsuarioPorRut(String rut) {
        Usuario usuario = usuarioRepository.findByRut(rut)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return convertirADTO(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioDTO> obtenerUsuariosPorRol(String rol) {
        return usuarioRepository.findByRolesNombre(rol)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void activarUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuario.setActivo(true);
        usuarioRepository.save(usuario);
    }

    @Override
    @Transactional
    public void desactivarUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuario.setActivo(false);
        usuarioRepository.save(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario getUsuarioEntity(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }
}