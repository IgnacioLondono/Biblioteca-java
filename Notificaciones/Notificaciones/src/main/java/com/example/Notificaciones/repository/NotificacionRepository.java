package com.example.Notificaciones.repository;

import com.example.Notificaciones.model.EstadoNotificacion;
import com.example.Notificaciones.model.Notificacion;
import com.example.Notificaciones.model.TipoNotificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
    
    List<Notificacion> findByUsuarioId(Long usuarioId);
    List<Notificacion> findByEstado(EstadoNotificacion estado);
    List<Notificacion> findByTipo(TipoNotificacion tipo);
    List<Notificacion> findByUsuarioIdAndEstado(Long usuarioId, EstadoNotificacion estado);
    List<Notificacion> findByFechaCreacionBetween(LocalDateTime inicio, LocalDateTime fin);
    
    @Query("SELECT n FROM Notificacion n WHERE n.estado = 'PENDIENTE' AND n.intentosEnvio < 3")
    List<Notificacion> findPendientesConReintentos();
    
    Long countByUsuarioIdAndEstado(Long usuarioId, EstadoNotificacion estado);
    
    @Query("SELECT n FROM Notificacion n WHERE n.usuarioId = :usuarioId AND n.fechaCreacion >= :fecha ORDER BY n.fechaCreacion DESC")
    List<Notificacion> findRecientesByUsuarioId(@Param("usuarioId") Long usuarioId, @Param("fecha") LocalDateTime fecha);
}