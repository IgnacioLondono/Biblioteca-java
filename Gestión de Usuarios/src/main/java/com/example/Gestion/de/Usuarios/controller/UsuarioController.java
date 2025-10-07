package com.example.Gestion.de.Usuarios.controller;

import com.example.Gestion.de.Usuarios.dto.UsuarioCreateRequest;
import com.example.Gestion.de.Usuarios.dto.UsuarioDTO;
import com.example.Gestion.de.Usuarios.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuarios", description = "API para gestión de usuarios")
@SecurityRequirement(name = "bearerAuth")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    @Operation(summary = "Crear usuario", description = "Crea un nuevo usuario en el sistema")
    public ResponseEntity<UsuarioDTO> crearUsuario(@Valid @RequestBody UsuarioCreateRequest request) {
        UsuarioDTO usuarioCreado = usuarioService.crearUsuario(request);
        return ResponseEntity.ok(usuarioCreado);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USUARIO') and @usuarioServiceImpl.getUsuarioEntity(#id).email == authentication.principal.username)")
    @Operation(summary = "Actualizar usuario", description = "Actualiza los datos de un usuario")
    public ResponseEntity<UsuarioDTO> actualizarUsuario(
            @PathVariable Long id, 
            @Valid @RequestBody UsuarioCreateRequest request) {
        UsuarioDTO usuarioActualizado = usuarioService.actualizarUsuario(id, request);
        return ResponseEntity.ok(usuarioActualizado);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario (desactiva)")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USUARIO') and @usuarioServiceImpl.getUsuarioEntity(#id).email == authentication.principal.username)")
    @Operation(summary = "Obtener usuario por ID", description = "Obtiene los datos de un usuario por su ID")
    public ResponseEntity<UsuarioDTO> obtenerUsuarioPorId(@PathVariable Long id) {
        UsuarioDTO usuario = usuarioService.obtenerUsuarioPorId(id);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Listar usuarios", description = "Obtiene la lista de todos los usuarios activos")
    public ResponseEntity<List<UsuarioDTO>> obtenerTodosLosUsuarios() {
        List<UsuarioDTO> usuarios = usuarioService.obtenerTodosLosUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/email/{email}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Obtener usuario por email", description = "Obtiene un usuario por su email")
    public ResponseEntity<UsuarioDTO> obtenerUsuarioPorEmail(@PathVariable String email) {
        UsuarioDTO usuario = usuarioService.obtenerUsuarioPorEmail(email);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/rut/{rut}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Obtener usuario por RUT", description = "Obtiene un usuario por su RUT")
    public ResponseEntity<UsuarioDTO> obtenerUsuarioPorRut(@PathVariable String rut) {
        UsuarioDTO usuario = usuarioService.obtenerUsuarioPorRut(rut);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/rol/{rol}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Obtener usuarios por rol", description = "Obtiene usuarios por rol específico")
    public ResponseEntity<List<UsuarioDTO>> obtenerUsuariosPorRol(@PathVariable String rol) {
        List<UsuarioDTO> usuarios = usuarioService.obtenerUsuariosPorRol(rol);
        return ResponseEntity.ok(usuarios);
    }

    @PatchMapping("/{id}/activar")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Activar usuario", description = "Activa un usuario previamente desactivado")
    public ResponseEntity<Void> activarUsuario(@PathVariable Long id) {
        usuarioService.activarUsuario(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/desactivar")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Desactivar usuario", description = "Desactiva un usuario")
    public ResponseEntity<Void> desactivarUsuario(@PathVariable Long id) {
        usuarioService.desactivarUsuario(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Obtener mi perfil", description = "Obtiene los datos del usuario autenticado")
    public ResponseEntity<UsuarioDTO> obtenerMiPerfil() {
        // Este endpoint debería implementarse para obtener el usuario actual
        // Por ahora retornamos un mensaje de que está en desarrollo
        throw new RuntimeException("Endpoint en desarrollo");
    }
}