package com.example.Gestion.de.prestamos.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
@ConfigurationProperties(prefix = "prestamos")
public class PrestamosProperties {
    private Integer diasPorDefecto = 15;
    private Integer maxRenovaciones = 2;
    private BigDecimal tarifaDiaria = new BigDecimal("1.50");

    public Integer getDiasPorDefecto() { return diasPorDefecto; }
    public void setDiasPorDefecto(Integer diasPorDefecto) { this.diasPorDefecto = diasPorDefecto; }

    public Integer getMaxRenovaciones() { return maxRenovaciones; }
    public void setMaxRenovaciones(Integer maxRenovaciones) { this.maxRenovaciones = maxRenovaciones; }

    public BigDecimal getTarifaDiaria() { return tarifaDiaria; }
    public void setTarifaDiaria(BigDecimal tarifaDiaria) { this.tarifaDiaria = tarifaDiaria; }
}
