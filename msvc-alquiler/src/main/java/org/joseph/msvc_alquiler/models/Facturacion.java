package org.joseph.msvc_alquiler.models;

import jakarta.persistence.Embeddable;

import java.time.LocalDate;

@Embeddable
public class Facturacion {

    private String numeroFactura;
    private LocalDate emisionFactura;

    // Getters y Setters
    public String getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public LocalDate getEmisionFactura() {
        return emisionFactura;
    }

    public void setEmisionFactura(LocalDate emisionFactura) {
        this.emisionFactura = emisionFactura;
    }
}
