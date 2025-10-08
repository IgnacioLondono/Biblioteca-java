package com.example.Gestion.de.Usuarios.config;

import com.example.Gestion.de.Usuarios.model.*;
import com.example.Gestion.de.Usuarios.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class DataInitializer implements ApplicationRunner {

    private final RegionRepository regionRepository;
    private final ComunaRepository comunaRepository;
    private final DireccionRepository direccionRepository;
    private final RolRepository rolRepository;
    private final UsuarioRepository usuarioRepository;
    private final MultaRepository multaRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataInitializer(
            RegionRepository regionRepository,
            ComunaRepository comunaRepository,
            DireccionRepository direccionRepository,
            RolRepository rolRepository,
            UsuarioRepository usuarioRepository,
            MultaRepository multaRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.regionRepository = regionRepository;
        this.comunaRepository = comunaRepository;
        this.direccionRepository = direccionRepository;
        this.rolRepository = rolRepository;
        this.usuarioRepository = usuarioRepository;
        this.multaRepository = multaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        // 1) Regiones
        Region rm = ensureRegion("RM", "Región Metropolitana de Santiago", "13");
        Region v = ensureRegion("V", "Región de Valparaíso", "05");

        // 2) Comunas
        Comuna santiago = ensureComuna("SCL", "Santiago", rm);
        Comuna maipu = ensureComuna("MAI", "Maipú", rm);
        Comuna valparaiso = ensureComuna("VAP", "Valparaíso", v);

        // 3) Roles y permisos
        Rol adminRole = ensureRol("ADMIN", "Administrador del sistema",
                Arrays.asList("USERS_READ", "USERS_WRITE", "USERS_DELETE"));
        Rol userRole = ensureRol("USER", "Usuario estándar",
                Arrays.asList("USERS_READ"));

        // 4) Usuarios con direcciones
        ensureUsuarioConDireccion(
                "11.111.111-1",
                "Admin",
                "Principal",
                "admin@demo.cl",
                "admin123",
                "+56911111111",
                santiago,
                "Calle Falsa", "123", null, "8320000",
                Arrays.asList(adminRole, userRole)
        );

        ensureUsuarioConDireccion(
                "22.222.222-2",
                "Juan",
                "Pérez",
                "user@demo.cl",
                "user123",
                "+56922222222",
                maipu,
                "Av. Siempre Viva", "742", "201", "9250000",
                Arrays.asList(userRole)
        );

        // 5) Multas para el usuario estándar
        usuarioRepository.findByEmail("user@demo.cl").ifPresent(user -> {
            List<Multa> existentes = multaRepository.findByUsuarioId(user.getId());
            if (existentes.isEmpty()) {
                Multa m1 = Multa.builder()
                        .usuario(user)
                        .monto(new BigDecimal("5000"))
                        .motivo("Retraso en devolución de libro")
                        .fechaVencimiento(LocalDateTime.now().plusDays(30))
                        .build();

                Multa m2 = Multa.builder()
                        .usuario(user)
                        .monto(new BigDecimal("12000"))
                        .motivo("Daño en material bibliográfico")
                        .fechaVencimiento(LocalDateTime.now().plusDays(45))
                        .build();

                multaRepository.saveAll(Arrays.asList(m1, m2));
            }
        });
    }

    private Region ensureRegion(String codigo, String nombre, String regionNumerica) {
        return regionRepository.findByCodigo(codigo).orElseGet(() -> {
            Region r = new Region();
            r.setCodigo(codigo);
            r.setNombre(nombre);
            r.setRegionNumerica(regionNumerica);
            return regionRepository.save(r);
        });
    }

    private Comuna ensureComuna(String codigo, String nombre, Region region) {
        return comunaRepository.findByCodigo(codigo).orElseGet(() -> {
            Comuna c = new Comuna();
            c.setCodigo(codigo);
            c.setNombre(nombre);
            c.setRegion(region);
            return comunaRepository.save(c);
        });
    }

    private Rol ensureRol(String nombre, String descripcion, List<String> permisos) {
        Optional<Rol> opt = rolRepository.findByNombre(nombre);
        if (opt.isPresent()) {
            Rol existente = opt.get();
            existente.setDescripcion(descripcion);
            // ✅ Convertir a lista mutable
            existente.setPermisos(new ArrayList<>(permisos));
            return rolRepository.save(existente);
        }
        Rol r = new Rol();
        r.setNombre(nombre);
        r.setDescripcion(descripcion);
        // ✅ Lista mutable
        r.setPermisos(new ArrayList<>(permisos));
        // ✅ Inicializar usuarios como lista vacía mutable
        r.setUsuarios(new ArrayList<>());
        return rolRepository.save(r);
    }

    private void ensureUsuarioConDireccion(
            String rut,
            String nombre,
            String apellido,
            String email,
            String passwordPlano,
            String telefono,
            Comuna comuna,
            String calle,
            String numero,
            String departamento,
            String codigoPostal,
            List<Rol> roles
    ) {
        if (usuarioRepository.existsByEmail(email) || usuarioRepository.existsByRut(rut)) {
            return;
        }

        Direccion dir = Direccion.builder()
                .calle(calle)
                .numero(numero)
                .departamento(departamento)
                .codigoPostal(codigoPostal)
                .comuna(comuna)
                .build();
        dir = direccionRepository.save(dir);

        Usuario u = new Usuario();
        u.setRut(rut);
        u.setNombre(nombre);
        u.setApellido(apellido);
        u.setEmail(email);
        u.setPassword(passwordEncoder.encode(passwordPlano));
        u.setTelefono(telefono);
        u.setDireccion(dir);
        // ✅ Lista mutable
        u.setRoles(new ArrayList<>(roles));
        u.setActivo(true);
        u.setFechaCreacion(LocalDateTime.now());
        u.setFechaActualizacion(LocalDateTime.now());

        usuarioRepository.save(u);
    }
}
