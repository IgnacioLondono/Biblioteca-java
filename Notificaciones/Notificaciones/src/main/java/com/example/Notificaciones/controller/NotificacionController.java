package com.example.Notificaciones.controller;

import com.example.Notificaciones.dto.MensajeResponse;
import com.example.Notificaciones.dto.NotificacionDTO;
import com.example.Notificaciones.dto.NotificacionRequest;
import com.example.Notificaciones.service.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {

    private final NotificacionService notificacionService;

    @Autowired
    public NotificacionController(NotificacionService notificacionService) {
        this.notificacionService = notificacionService;
    }

    @PostMapping
    public ResponseEntity<NotificacionDTO> crearNotificacion(@RequestBody NotificacionRequest request) {
        NotificacionDTO notificacionCreada = notificacionService.crearNotificacion(request);
        return ResponseEntity.ok(notificacionCreada);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<NotificacionDTO>> obtenerNotificacionesPorUsuario(@PathVariable Long usuarioId) {
        List<NotificacionDTO> notificaciones = notificacionService.obtenerNotificacionesPorUsuario(usuarioId);
        return ResponseEntity.ok(notificaciones);
    }

    @GetMapping("/pendientes")
    public ResponseEntity<List<NotificacionDTO>> obtenerNotificacionesPendientes() {
        List<NotificacionDTO> notificaciones = notificacionService.obtenerNotificacionesPendientes();
        return ResponseEntity.ok(notificaciones);
    }

    @PatchMapping("/{id}/enviar")
    public ResponseEntity<NotificacionDTO> marcarComoEnviada(@PathVariable Long id) {
        NotificacionDTO notificacion = notificacionService.marcarComoEnviada(id);
        return ResponseEntity.ok(notificacion);
    }

    @PatchMapping("/{id}/leer")
    public ResponseEntity<NotificacionDTO> marcarComoLeida(@PathVariable Long id) {
        NotificacionDTO notificacion = notificacionService.marcarComoLeida(id);
        return ResponseEntity.ok(notificacion);
    }

    @PatchMapping("/{id}/fallar")
    public ResponseEntity<NotificacionDTO> marcarComoFallida(@PathVariable Long id, @RequestParam String error) {
        NotificacionDTO notificacion = notificacionService.marcarComoFallida(id, error);
        return ResponseEntity.ok(notificacion);
    }

    @PostMapping("/procesar-pendientes")
    public ResponseEntity<List<NotificacionDTO>> procesarNotificacionesPendientes() {
        List<NotificacionDTO> notificaciones = notificacionService.procesarNotificacionesPendientes();
        return ResponseEntity.ok(notificaciones);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MensajeResponse> eliminarNotificacion(@PathVariable Long id) {
        notificacionService.eliminarNotificacion(id);
        return ResponseEntity.ok(new MensajeResponse("Notificación eliminada exitosamente"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificacionDTO> obtenerNotificacionPorId(@PathVariable Long id) {
        NotificacionDTO notificacion = notificacionService.obtenerNotificacionPorId(id);
        return ResponseEntity.ok(notificacion);
    }

    @GetMapping
    public ResponseEntity<List<NotificacionDTO>> obtenerTodasLasNotificaciones() {
        List<NotificacionDTO> notificaciones = notificacionService.obtenerTodasLasNotificaciones();
        return ResponseEntity.ok(notificaciones);
    }

    @GetMapping("/usuario/{usuarioId}/pendientes/count")
    public ResponseEntity<Long> contarNotificacionesPendientesUsuario(@PathVariable Long usuarioId) {
        Long count = notificacionService.contarNotificacionesPendientesUsuario(usuarioId);
        return ResponseEntity.ok(count);
    }
}