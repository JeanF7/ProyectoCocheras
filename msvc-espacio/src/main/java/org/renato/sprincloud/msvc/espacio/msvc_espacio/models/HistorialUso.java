package org.renato.sprincloud.msvc.espacio.msvc_espacio.models;

import jakarta.persistence.Embeddable;

import java.util.Date;

@Embeddable
public class HistorialUso {
    private Date fechaInicio;
    private Date fechaFin;

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }
}
