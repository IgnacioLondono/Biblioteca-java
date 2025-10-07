package com.example.Gestion.de.Usuarios.security;

import com.example.Gestion.de.Usuarios.model.Usuario;
import com.example.Gestion.de.Usuarios.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UserDetailsServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + email));

        // Verificar si el usuario está activo - acceso directo al campo
        Boolean activo = getActivo(usuario);
        if (activo == null || !activo) {
            throw new UsernameNotFoundException("Usuario desactivado: " + email);
        }

        // Obtener email y password - acceso directo
        String userEmail = getEmail(usuario);
        String password = getPassword(usuario);

        // Construir authorities manualmente
        List<SimpleGrantedAuthority> authorities = buildAuthorities(usuario);

        return new User(
                userEmail,
                password != null ? password : "",
                true,  // enabled
                true,  // accountNonExpired
                true,  // credentialsNonExpired
                true,  // accountNonLocked
                authorities
        );
    }

    // Métodos de acceso directo para evitar problemas con getters
    private Boolean getActivo(Usuario usuario) {
        try {
            return usuario.getActivo();
        } catch (Exception e) {
            // Si falla el getter, intentar acceso directo
            try {
                java.lang.reflect.Field field = Usuario.class.getDeclaredField("activo");
                field.setAccessible(true);
                return (Boolean) field.get(usuario);
            } catch (Exception ex) {
                return false;
            }
        }
    }

    private String getEmail(Usuario usuario) {
        try {
            return usuario.getEmail();
        } catch (Exception e) {
            try {
                java.lang.reflect.Field field = Usuario.class.getDeclaredField("email");
                field.setAccessible(true);
                return (String) field.get(usuario);
            } catch (Exception ex) {
                return "";
            }
        }
    }

    private String getPassword(Usuario usuario) {
        try {
            return usuario.getPassword();
        } catch (Exception e) {
            try {
                java.lang.reflect.Field field = Usuario.class.getDeclaredField("password");
                field.setAccessible(true);
                return (String) field.get(usuario);
            } catch (Exception ex) {
                return "";
            }
        }
    }

    private List<SimpleGrantedAuthority> buildAuthorities(Usuario usuario) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        
        try {
            // Obtener roles - manejo seguro
            Object rolesObj = null;
            try {
                rolesObj = usuario.getRoles();
            } catch (Exception e) {
                try {
                    java.lang.reflect.Field field = Usuario.class.getDeclaredField("roles");
                    field.setAccessible(true);
                    rolesObj = field.get(usuario);
                } catch (Exception ex) {
                    // Si no se puede acceder a roles, retornar lista vacía
                    return authorities;
                }
            }

            if (rolesObj instanceof List) {
                List<?> roles = (List<?>) rolesObj;
                
                for (Object rolObj : roles) {
                    if (rolObj != null) {
                        // Obtener nombre del rol
                        String nombreRol = getRolNombre(rolObj);
                        if (nombreRol != null && !nombreRol.trim().isEmpty()) {
                            authorities.add(new SimpleGrantedAuthority("ROLE_" + nombreRol));
                        }

                        // Obtener permisos del rol
                        List<String> permisos = getRolPermisos(rolObj);
                        if (permisos != null) {
                            for (String permiso : permisos) {
                                if (permiso != null && !permiso.trim().isEmpty()) {
                                    authorities.add(new SimpleGrantedAuthority(permiso));
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            // En caso de error, continuar con autoridades vacías
            System.err.println("Error construyendo autoridades: " + e.getMessage());
        }

        return authorities;
    }

    private String getRolNombre(Object rolObj) {
        try {
            java.lang.reflect.Method method = rolObj.getClass().getMethod("getNombre");
            return (String) method.invoke(rolObj);
        } catch (Exception e) {
            try {
                java.lang.reflect.Field field = rolObj.getClass().getDeclaredField("nombre");
                field.setAccessible(true);
                return (String) field.get(rolObj);
            } catch (Exception ex) {
                return null;
            }
        }
    }

    @SuppressWarnings("unchecked")
    private List<String> getRolPermisos(Object rolObj) {
        try {
            java.lang.reflect.Method method = rolObj.getClass().getMethod("getPermisos");
            return (List<String>) method.invoke(rolObj);
        } catch (Exception e) {
            try {
                java.lang.reflect.Field field = rolObj.getClass().getDeclaredField("permisos");
                field.setAccessible(true);
                return (List<String>) field.get(rolObj);
            } catch (Exception ex) {
                return null;
            }
        }
    }
}