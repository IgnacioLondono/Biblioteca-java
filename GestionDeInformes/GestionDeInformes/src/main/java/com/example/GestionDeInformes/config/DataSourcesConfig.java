package com.example.GestionDeInformes.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class DataSourcesConfig {

    // ============ DataSource USUARIOS ============
    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.usuarios")
    public DataSourceProperties usuariosDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "usuariosDataSource")
    @Primary
    public DataSource usuariosDataSource(@Qualifier("usuariosDataSourceProperties") DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build();
    }

    @Bean(name = "usuariosEntityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean usuariosEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("usuariosDataSource") DataSource dataSource
    ) {
        return builder
                .dataSource(dataSource)
                .packages("com.example.GestionDeInformes.usuarios.model")
                .persistenceUnit("usuariosPU")
                .build();
    }

    @Bean(name = "usuariosTransactionManager")
    @Primary
    public PlatformTransactionManager usuariosTransactionManager(
            @Qualifier("usuariosEntityManagerFactory") LocalContainerEntityManagerFactoryBean emf
    ) {
        return new JpaTransactionManager(emf.getObject());
    }

    @Configuration
    @EnableJpaRepositories(
            basePackages = "com.example.GestionDeInformes.usuarios.repository",
            entityManagerFactoryRef = "usuariosEntityManagerFactory",
            transactionManagerRef = "usuariosTransactionManager"
    )
    static class UsuariosJpaRepositoriesConfig { }

    // ============ DataSource PRESTAMOS ============
    @Bean
    @ConfigurationProperties("spring.datasource.prestamos")
    public DataSourceProperties prestamosDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "prestamosDataSource")
    public DataSource prestamosDataSource(@Qualifier("prestamosDataSourceProperties") DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build();
    }

    @Bean(name = "prestamosEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean prestamosEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("prestamosDataSource") DataSource dataSource
    ) {
        return builder
                .dataSource(dataSource)
                .packages("com.example.GestionDeInformes.prestamos.model")
                .persistenceUnit("prestamosPU")
                .build();
    }

    @Bean(name = "prestamosTransactionManager")
    public PlatformTransactionManager prestamosTransactionManager(
            @Qualifier("prestamosEntityManagerFactory") LocalContainerEntityManagerFactoryBean emf
    ) {
        return new JpaTransactionManager(emf.getObject());
    }

    @Configuration
    @EnableJpaRepositories(
            basePackages = "com.example.GestionDeInformes.prestamos.repository",
            entityManagerFactoryRef = "prestamosEntityManagerFactory",
            transactionManagerRef = "prestamosTransactionManager"
    )
    static class PrestamosJpaRepositoriesConfig { }
}
