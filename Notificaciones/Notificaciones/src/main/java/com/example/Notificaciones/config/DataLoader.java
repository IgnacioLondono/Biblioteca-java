package com.example.Notificaciones.config;

import com.example.Notificaciones.model.Notificacion;
import com.example.Notificaciones.model.TipoNotificacion;
import com.example.Notificaciones.repository.NotificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final NotificacionRepository notificacionRepository;

    @Autowired
    public DataLoader(NotificacionRepository notificacionRepository) {
        this.notificacionRepository = notificacionRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        cargarDatosDePrueba();
        System.out.println("=== DATOS DE NOTIFICACIONES CARGADOS EXITOSAMENTE ===");
    }

    private void cargarDatosDePrueba() {
        if (notificacionRepository.count() == 0) {
            // Notificaciones de ejemplo
            Notificacion notif1 = new Notificacion(
                1L,
                "Préstamo Registrado",
                "Se ha registrado su préstamo del libro 'Cien Años de Soledad'. Fecha de devolución: 2024-01-15",
                TipoNotificacion.PRESTAMO_CREADO,
                "usuario1@email.com"
            );

            Notificacion notif2 = new Notificacion(
                2L,
                "Recordatorio Devolución",
                "Le recordamos que mañana vence el plazo para devolver 'La Casa de los Espíritus'",
                TipoNotificacion.DEVOLUCION_PROXIMA,
                "usuario2@email.com"
            );

            Notificacion notif3 = new Notificacion(
                1L,
                "Multa Asignada",
                "Se le ha asignado una multa de $5.000 por devolución tardía",
                TipoNotificacion.MULTA_ASIGNADA,
                "usuario1@email.com"
            );

            notificacionRepository.save(notif1);
            notificacionRepository.save(notif2);
            notificacionRepository.save(notif3);
        }
    }
}