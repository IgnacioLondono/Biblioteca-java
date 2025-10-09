package com.example.Notificaciones.service;

import com.example.Notificaciones.dto.NotificacionDTO;
import com.example.Notificaciones.dto.NotificacionRequest;
import com.example.Notificaciones.model.EstadoNotificacion;
import com.example.Notificaciones.model.TipoNotificacion;

import java.util.List;

public interface NotificacionService {
    NotificacionDTO crearNotificacion(NotificacionRequest request);
    List<NotificacionDTO> obtenerNotificacionesPorUsuario(Long usuarioId);
    List<NotificacionDTO> obtenerNotificacionesPendientes();
    NotificacionDTO marcarComoEnviada(Long id);
    NotificacionDTO marcarComoLeida(Long id);
    NotificacionDTO marcarComoFallida(Long id, String error);
    List<NotificacionDTO> procesarNotificacionesPendientes();
    void eliminarNotificacion(Long id);
    NotificacionDTO obtenerNotificacionPorId(Long id);
    List<NotificacionDTO> obtenerTodasLasNotificaciones();
    Long contarNotificacionesPendientesUsuario(Long usuarioId);
}