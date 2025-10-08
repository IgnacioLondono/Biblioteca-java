package com.example.LibrosCatalogo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Microservicio de Catálogo de Libros - Biblioteca")
                        .version("1.0.0")
                        .description("API REST para la gestión del catálogo de libros de la biblioteca. " +
                                   "Incluye operaciones CRUD para libros, autores, categorías y búsquedas avanzadas.")
                        .contact(new Contact()
                                .name("Equipo de Desarrollo")
                                .email("desarrollo@biblioteca.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")));
    }
}