package com.example.Gestion.de.prestamos.application.controller;

import com.example.Gestion.de.prestamos.application.dto.MultaDTO;
import com.example.Gestion.de.prestamos.application.mapper.MultaMapper;
import com.example.Gestion.de.prestamos.domain.model.Multa;
import com.example.Gestion.de.prestamos.domain.repository.MultaRepository;
import com.example.Gestion.de.prestamos.domain.repository.PrestamoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/v1/multas")
public class MultaController {

    private final MultaRepository multaRepository;
    private final PrestamoRepository prestamoRepository;

    public MultaController(MultaRepository multaRepository, PrestamoRepository prestamoRepository) {
        this.multaRepository = multaRepository;
        this.prestamoRepository = prestamoRepository;
    }

    @GetMapping("/prestamo/{prestamoId}")
    public ResponseEntity<List<MultaDTO>> listarPorPrestamo(@PathVariable Long prestamoId) {
        return prestamoRepository.findById(prestamoId)
                .map(p -> multaRepository.findByPrestamo(p).stream().map(MultaMapper::toDTO).toList())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/pagar")
    public ResponseEntity<MultaDTO> pagar(@PathVariable Long id) {
        return multaRepository.findById(id).map(m -> {
            m.setEstado(Multa.MultaEstado.PAGADA);
            m.setFechaPago(Instant.now());
            Multa guardada = multaRepository.save(m);
            return ResponseEntity.ok(MultaMapper.toDTO(guardada));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/eximir")
    public ResponseEntity<MultaDTO> eximir(@PathVariable Long id, @RequestParam(name = "motivo", required = false) String motivo) {
        return multaRepository.findById(id).map(m -> {
            m.setEstado(Multa.MultaEstado.EXENTA);
            m.setFechaPago(Instant.now());
            m.setMotivo(motivo);
            Multa guardada = multaRepository.save(m);
            return ResponseEntity.ok(MultaMapper.toDTO(guardada));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
