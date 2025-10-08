package com.example.Gestion.de.prestamos.application.mapper;

import com.example.Gestion.de.prestamos.application.dto.PrestamoDTO;
import com.example.Gestion.de.prestamos.domain.model.Prestamo;

public final class PrestamoMapper {
    private PrestamoMapper() {}

    public static PrestamoDTO toDTO(Prestamo p) {
        if (p == null) return null;
        return PrestamoDTO.builder()
                .id(p.getId())
                .usuarioId(p.getUsuarioId())
                .ejemplarId(p.getEjemplarId())
                .fechaPrestamo(p.getFechaPrestamo())
                .fechaVencimiento(p.getFechaVencimiento())
                .fechaDevolucion(p.getFechaDevolucion())
                .estado(p.getEstado().name())
                .renovaciones(p.getRenovaciones())
                .build();
    }

    public static Prestamo toEntity(PrestamoDTO dto) {
        if (dto == null) return null;
        Prestamo.PrestamoEstado estado = null;
        if (dto.getEstado() != null && !dto.getEstado().isBlank()) {
            estado = Prestamo.PrestamoEstado.valueOf(dto.getEstado());
        }
        return Prestamo.builder()
                .id(dto.getId())
                .usuarioId(dto.getUsuarioId())
                .ejemplarId(dto.getEjemplarId())
                .fechaPrestamo(dto.getFechaPrestamo())
                .fechaVencimiento(dto.getFechaVencimiento())
                .fechaDevolucion(dto.getFechaDevolucion())
                .estado(estado)
                .renovaciones(dto.getRenovaciones())
                .build();
    }
}
