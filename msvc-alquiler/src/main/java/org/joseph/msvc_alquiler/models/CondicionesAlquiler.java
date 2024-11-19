package org.joseph.msvc_alquiler.models;

import jakarta.persistence.Embeddable;

@Embeddable
public class CondicionesAlquiler {

    private String tipoContrato;
    private String terminosYCondiciones;

    // Getters y Setters
    public String getTipoContrato() {
        return tipoContrato;
    }

    public void setTipoContrato(String tipoContrato) {
        this.tipoContrato = tipoContrato;
    }

    public String getTerminosYCondiciones() {
        return terminosYCondiciones;
    }

    public void setTerminosYCondiciones(String terminosYCondiciones) {
        this.terminosYCondiciones = terminosYCondiciones;
    }
}
