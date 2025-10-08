package com.example.Gestion.de.prestamos.domain.service;

import com.example.Gestion.de.prestamos.application.dto.PrestamoDTO;
import com.example.Gestion.de.prestamos.application.mapper.PrestamoMapper;
import com.example.Gestion.de.prestamos.config.PrestamosProperties;
import com.example.Gestion.de.prestamos.domain.model.Multa;
import com.example.Gestion.de.prestamos.domain.model.Prestamo;
import com.example.Gestion.de.prestamos.domain.repository.MultaRepository;
import com.example.Gestion.de.prestamos.domain.repository.PrestamoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class PrestamoService {

    private final PrestamoRepository prestamoRepository;
    private final MultaRepository multaRepository;
    private final PrestamosProperties properties;

    public PrestamoService(PrestamoRepository prestamoRepository,
                           MultaRepository multaRepository,
                           PrestamosProperties properties) {
        this.prestamoRepository = prestamoRepository;
        this.multaRepository = multaRepository;
        this.properties = properties;
    }

    @Transactional
    public PrestamoDTO crearPrestamo(PrestamoDTO dto) {
        // Validación simple: no permitir prestar un ejemplar si está ACTIVO o ATRASO
        boolean enUso = prestamoRepository.existsByEjemplarIdAndEstadoIn(
                dto.getEjemplarId(),
                List.of(Prestamo.PrestamoEstado.ACTIVO, Prestamo.PrestamoEstado.ATRASO)
        );
        if (enUso) {
            throw new IllegalStateException("El ejemplar ya está prestado");
        }

        Prestamo entidad = PrestamoMapper.toEntity(dto);
        if (entidad.getFechaPrestamo() == null) {
            entidad.setFechaPrestamo(LocalDate.now());
        }
        if (entidad.getFechaVencimiento() == null) {
            entidad.setFechaVencimiento(entidad.getFechaPrestamo().plusDays(properties.getDiasPorDefecto()));
        }
        if (entidad.getEstado() == null) {
            entidad.setEstado(Prestamo.PrestamoEstado.ACTIVO);
        }
        if (entidad.getRenovaciones() == null) {
            entidad.setRenovaciones(0);
        }

        Prestamo guardado = prestamoRepository.save(entidad);
        return PrestamoMapper.toDTO(guardado);
    }

    public Optional<PrestamoDTO> obtenerPorId(Long id) {
        return prestamoRepository.findById(id).map(PrestamoMapper::toDTO);
    }

    public List<PrestamoDTO> listarPorUsuario(Long usuarioId) {
        return prestamoRepository.findByUsuarioId(usuarioId)
                .stream().map(PrestamoMapper::toDTO).toList();
    }

    public List<PrestamoDTO> listarPorEstado(String estado) {
        Prestamo.PrestamoEstado e = Prestamo.PrestamoEstado.valueOf(estado);
        return prestamoRepository.findByEstado(e)
                .stream().map(PrestamoMapper::toDTO).toList();
    }

    @Transactional
    public PrestamoDTO renovar(Long idPrestamo) {
        Prestamo prestamo = prestamoRepository.findById(idPrestamo)
                .orElseThrow(() -> new IllegalArgumentException("Préstamo no encontrado"));
        if (prestamo.getEstado() != Prestamo.PrestamoEstado.ACTIVO && prestamo.getEstado() != Prestamo.PrestamoEstado.ATRASO) {
            throw new IllegalStateException("Solo se puede renovar un préstamo ACTIVO o en ATRASO");
        }
        if (prestamo.getRenovaciones() >= properties.getMaxRenovaciones()) {
            throw new IllegalStateException("Se alcanzó el máximo de renovaciones");
        }
        prestamo.setRenovaciones(prestamo.getRenovaciones() + 1);
        prestamo.setFechaVencimiento(prestamo.getFechaVencimiento().plusDays(properties.getDiasPorDefecto()));
        // Si estaba en ATRASO pero se renueva, se vuelve ACTIVO (opcional)
        if (prestamo.getEstado() == Prestamo.PrestamoEstado.ATRASO) {
            prestamo.setEstado(Prestamo.PrestamoEstado.ACTIVO);
        }
        return PrestamoMapper.toDTO(prestamoRepository.save(prestamo));
    }

    @Transactional
    public PrestamoDTO devolver(Long idPrestamo) {
        Prestamo prestamo = prestamoRepository.findById(idPrestamo)
                .orElseThrow(() -> new IllegalArgumentException("Préstamo no encontrado"));
        if (prestamo.getEstado() == Prestamo.PrestamoEstado.DEVUELTO) {
            return PrestamoMapper.toDTO(prestamo); // idempotente
        }
        prestamo.setFechaDevolucion(LocalDate.now());
        prestamo.setEstado(Prestamo.PrestamoEstado.DEVUELTO);
        return PrestamoMapper.toDTO(prestamoRepository.save(prestamo));
    }

    @Transactional
    public void evaluarAtrasosYGenerarMultas() {
        LocalDate hoy = LocalDate.now();
        List<Prestamo> vencidos = prestamoRepository.findByEstadoNotAndFechaVencimientoBefore(Prestamo.PrestamoEstado.DEVUELTO, hoy);
        for (Prestamo p : vencidos) {
            long dias = ChronoUnit.DAYS.between(p.getFechaVencimiento(), hoy);
            if (dias <= 0) continue;
            p.setEstado(Prestamo.PrestamoEstado.ATRASO);
            BigDecimal monto = properties.getTarifaDiaria().multiply(BigDecimal.valueOf(dias));

            Multa multa = Multa.builder()
                    .prestamo(p)
                    .diasMora((int) dias)
                    .monto(monto)
                    .estado(Multa.MultaEstado.PENDIENTE)
                    .motivo("Devolución atrasada")
                    .build();
            multaRepository.save(multa);
            prestamoRepository.save(p);
        }
    }
}
