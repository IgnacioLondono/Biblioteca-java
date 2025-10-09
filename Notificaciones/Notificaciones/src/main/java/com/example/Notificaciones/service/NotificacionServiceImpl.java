package com.example.Notificaciones.service;

import com.example.Notificaciones.dto.NotificacionDTO;
import com.example.Notificaciones.dto.NotificacionRequest;
import com.example.Notificaciones.model.EstadoNotificacion;
import com.example.Notificaciones.model.Notificacion;
import com.example.Notificaciones.model.TipoNotificacion;
import com.example.Notificaciones.repository.NotificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificacionServiceImpl implements NotificacionService {

    private final NotificacionRepository notificacionRepository;
    private final EmailService emailService;

    @Autowired
    public NotificacionServiceImpl(NotificacionRepository notificacionRepository, EmailService emailService) {
        this.notificacionRepository = notificacionRepository;
        this.emailService = emailService;
    }

    @Override
    @Transactional
    public NotificacionDTO crearNotificacion(NotificacionRequest request) {
        Notificacion notificacion = new Notificacion(
            request.getUsuarioId(),
            request.getTitulo(),
            request.getMensaje(),
            request.getTipo(),
            request.getDestinatarioEmail()
        );

        Notificacion notificacionGuardada = notificacionRepository.save(notificacion);
        return convertirADTO(notificacionGuardada);
    }

    @Override
    public List<NotificacionDTO> obtenerNotificacionesPorUsuario(Long usuarioId) {
        List<Notificacion> notificaciones = notificacionRepository.findByUsuarioId(usuarioId);
        return notificaciones.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<NotificacionDTO> obtenerNotificacionesPendientes() {
        List<Notificacion> notificaciones = notificacionRepository.findByEstado(EstadoNotificacion.PENDIENTE);
        return notificaciones.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public NotificacionDTO marcarComoEnviada(Long id) {
        Notificacion notificacion = notificacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notificación no encontrada con ID: " + id));
        
        notificacion.setEstado(EstadoNotificacion.ENVIADA);
        notificacion.setFechaEnvio(LocalDateTime.now());
        
        Notificacion notificacionActualizada = notificacionRepository.save(notificacion);
        return convertirADTO(notificacionActualizada);
    }

    @Override
    @Transactional
    public NotificacionDTO marcarComoLeida(Long id) {
        Notificacion notificacion = notificacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notificación no encontrada con ID: " + id));
        
        notificacion.setEstado(EstadoNotificacion.LEIDA);
        notificacion.setFechaLectura(LocalDateTime.now());
        
        Notificacion notificacionActualizada = notificacionRepository.save(notificacion);
        return convertirADTO(notificacionActualizada);
    }

    @Override
    @Transactional
    public NotificacionDTO marcarComoFallida(Long id, String error) {
        Notificacion notificacion = notificacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notificación no encontrada con ID: " + id));
        
        notificacion.setEstado(EstadoNotificacion.FALLIDA);
        notificacion.setErrorMensaje(error);
        notificacion.setIntentosEnvio(notificacion.getIntentosEnvio() + 1);
        
        Notificacion notificacionActualizada = notificacionRepository.save(notificacion);
        return convertirADTO(notificacionActualizada);
    }

    @Override
    @Transactional
    public List<NotificacionDTO> procesarNotificacionesPendientes() {
        List<Notificacion> notificacionesPendientes = notificacionRepository.findPendientesConReintentos();
        
        return notificacionesPendientes.stream()
                .map(this::procesarNotificacion)
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void eliminarNotificacion(Long id) {
        Notificacion notificacion = notificacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notificación no encontrada con ID: " + id));
        notificacionRepository.delete(notificacion);
    }

    @Override
    public NotificacionDTO obtenerNotificacionPorId(Long id) {
        Notificacion notificacion = notificacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notificación no encontrada con ID: " + id));
        return convertirADTO(notificacion);
    }

    @Override
    public List<NotificacionDTO> obtenerTodasLasNotificaciones() {
        return notificacionRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Override
    public Long contarNotificacionesPendientesUsuario(Long usuarioId) {
        return notificacionRepository.countByUsuarioIdAndEstado(usuarioId, EstadoNotificacion.PENDIENTE);
    }

    private Notificacion procesarNotificacion(Notificacion notificacion) {
        try {
            // Intentar enviar por email si tiene destinatario
            if (notificacion.getDestinatarioEmail() != null && !notificacion.getDestinatarioEmail().isEmpty()) {
                emailService.enviarEmail(
                    notificacion.getDestinatarioEmail(),
                    notificacion.getTitulo(),
                    notificacion.getMensaje()
                );
                return marcarComoEnviadaInterno(notificacion);
            } else {
                // Para notificaciones sin email, marcarlas como enviadas directamente
                return marcarComoEnviadaInterno(notificacion);
            }
        } catch (Exception e) {
            notificacion.setErrorMensaje(e.getMessage());
            notificacion.setIntentosEnvio(notificacion.getIntentosEnvio() + 1);
            
            // Si supera los 3 intentos, marcar como fallida
            if (notificacion.getIntentosEnvio() >= 3) {
                notificacion.setEstado(EstadoNotificacion.FALLIDA);
            }
            
            return notificacionRepository.save(notificacion);
        }
    }

    private Notificacion marcarComoEnviadaInterno(Notificacion notificacion) {
        notificacion.setEstado(EstadoNotificacion.ENVIADA);
        notificacion.setFechaEnvio(LocalDateTime.now());
        return notificacionRepository.save(notificacion);
    }

    private NotificacionDTO convertirADTO(Notificacion notificacion) {
        NotificacionDTO dto = new NotificacionDTO();
        dto.setId(notificacion.getId());
        dto.setUsuarioId(notificacion.getUsuarioId());
        dto.setTitulo(notificacion.getTitulo());
        dto.setMensaje(notificacion.getMensaje());
        dto.setTipo(notificacion.getTipo());
        dto.setEstado(notificacion.getEstado());
        dto.setDestinatarioEmail(notificacion.getDestinatarioEmail());
        dto.setIntentosEnvio(notificacion.getIntentosEnvio());
        dto.setErrorMensaje(notificacion.getErrorMensaje());
        dto.setFechaCreacion(notificacion.getFechaCreacion());
        dto.setFechaEnvio(notificacion.getFechaEnvio());
        dto.setFechaLectura(notificacion.getFechaLectura());
        return dto;
    }
}