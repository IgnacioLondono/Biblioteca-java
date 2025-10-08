package com.example.Gestion.de.prestamos.application.scheduler;

import com.example.Gestion.de.prestamos.domain.service.PrestamoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MultaScheduler {

    private final PrestamoService prestamoService;

    public MultaScheduler(PrestamoService prestamoService) {
        this.prestamoService = prestamoService;
    }

    @Scheduled(cron = "${scheduler.multa.cron}")
    public void generarMultasPorAtraso() {
        prestamoService.evaluarAtrasosYGenerarMultas();
    }
}
