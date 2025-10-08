package com.example.Gestion.de.prestamos.application.mapper;

import com.example.Gestion.de.prestamos.application.dto.MultaDTO;
import com.example.Gestion.de.prestamos.domain.model.Multa;

public final class MultaMapper {
    private MultaMapper() {}

    public static MultaDTO toDTO(Multa m) {
        if (m == null) return null;
        return MultaDTO.builder()
                .id(m.getId())
                .prestamoId(m.getPrestamo().getId())
                .diasMora(m.getDiasMora())
                .monto(m.getMonto())
                .estado(m.getEstado().name())
                .motivo(m.getMotivo())
                .fechaGeneracion(m.getFechaGeneracion())
                .fechaPago(m.getFechaPago())
                .build();
    }
}
