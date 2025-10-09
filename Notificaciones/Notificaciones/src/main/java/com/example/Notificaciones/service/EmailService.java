package com.example.Notificaciones.service;

import org.springframework.stereotype.Service;

@Service
public class EmailService {

    public void enviarEmail(String destinatario, String asunto, String mensaje) {
        // Simulación de envío de email (sin dependencias externas)
        System.out.println("=== SIMULANDO ENVÍO DE EMAIL ===");
        System.out.println("Para: " + destinatario);
        System.out.println("Asunto: " + asunto);
        System.out.println("Mensaje: " + mensaje);
        System.out.println("=== EMAIL ENVIADO EXITOSAMENTE ===");
    }

    public void enviarNotificacionPrestamo(String email, String libroTitulo, String fechaDevolucion) {
        String asunto = "Préstamo de Libro - Biblioteca";
        String mensaje = String.format(
            "Estimado usuario,\n\n" +
            "Se ha registrado su préstamo del libro: \"%s\"\n" +
            "Fecha de devolución: %s\n\n" +
            "Recuerde devolver el libro a tiempo para evitar multas.\n\n" +
            "Saludos cordiales,\nSistema de Biblioteca",
            libroTitulo, fechaDevolucion
        );

        enviarEmail(email, asunto, mensaje);
    }

    public void enviarRecordatorioDevolucion(String email, String libroTitulo, String fechaDevolucion) {
        String asunto = "Recordatorio Devolución - Biblioteca";
        String mensaje = String.format(
            "Estimado usuario,\n\n" +
            "Le recordamos que mañana vence el plazo para devolver el libro: \"%s\"\n" +
            "Fecha de devolución: %s\n\n" +
            "Por favor, acérquese a la biblioteca para realizar la devolución.\n\n" +
            "Saludos cordiales,\nSistema de Biblioteca",
            libroTitulo, fechaDevolucion
        );

        enviarEmail(email, asunto, mensaje);
    }

    public void enviarNotificacionMulta(String email, String motivo, Double monto) {
        String asunto = "Multa Asignada - Biblioteca";
        String mensaje = String.format(
            "Estimado usuario,\n\n" +
            "Se le ha asignado una multa por: %s\n" +
            "Monto: $%.2f\n\n" +
            "Por favor, regularice su situación para poder seguir utilizando los servicios de la biblioteca.\n\n" +
            "Saludos cordiales,\nSistema de Biblioteca",
            motivo, monto
        );

        enviarEmail(email, asunto, mensaje);
    }
}
