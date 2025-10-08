package com.example.Gestion.de.prestamos.application.controller;

import com.example.Gestion.de.prestamos.application.dto.PrestamoDTO;
import com.example.Gestion.de.prestamos.domain.service.PrestamoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/prestamos")
public class PrestamoController {

    private final PrestamoService prestamoService;

    public PrestamoController(PrestamoService prestamoService) {
        this.prestamoService = prestamoService;
    }

    @PostMapping
    public ResponseEntity<PrestamoDTO> crear(@RequestBody @Valid PrestamoDTO dto) {
        PrestamoDTO creado = prestamoService.crearPrestamo(dto);
        return ResponseEntity.created(URI.create("/api/v1/prestamos/" + creado.getId())).body(creado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrestamoDTO> obtener(@PathVariable Long id) {
        return prestamoService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<PrestamoDTO> listarPorUsuario(@PathVariable Long usuarioId) {
        return prestamoService.listarPorUsuario(usuarioId);
    }

    @GetMapping("/estado/{estado}")
    public List<PrestamoDTO> listarPorEstado(@PathVariable String estado) {
        return prestamoService.listarPorEstado(estado);
    }

    @PostMapping("/{id}/renovar")
    public PrestamoDTO renovar(@PathVariable Long id) {
        return prestamoService.renovar(id);
    }

    @PostMapping("/{id}/devolver")
    public PrestamoDTO devolver(@PathVariable Long id) {
        return prestamoService.devolver(id);
    }
}
