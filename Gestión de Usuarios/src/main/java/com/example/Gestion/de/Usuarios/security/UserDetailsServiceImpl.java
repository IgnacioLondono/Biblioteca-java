package com.example.Gestion.de.Usuarios.security;

import com.example.Gestion.de.Usuarios.model.Usuario;
import com.example.Gestion.de.Usuarios.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + email));

        // Verificar si el usuario está activo
        Boolean activo = usuario.getActivo();
        if (activo == null || !activo) {
            throw new UsernameNotFoundException("Usuario desactivado: " + email);
        }

        // Obtener autoridades con manejo seguro de null
        var authorities = usuario.getRoles() != null ? 
            usuario.getRoles().stream()
                .flatMap(rol -> {
                    if (rol != null && rol.getPermisos() != null) {
                        return rol.getPermisos().stream();
                    }
                    return Collections.<String>emptyStream();
                })
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList()) :
            Collections.emptyList();

        return new User(
                usuario.getEmail(),
                usuario.getPassword() != null ? usuario.getPassword() : "",
                true, // enabled
                true, // accountNonExpired
                true, // credentialsNonExpired
                true, // accountNonLocked
                authorities
        );
    }
}