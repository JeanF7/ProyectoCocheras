package org.jordonez.springcloud.msvc.personal.entites.models;

import jakarta.persistence.Embeddable;

import java.time.LocalTime;

@Embeddable
public class TurnoTrabajo {

    private LocalTime horarioInicio;
    private LocalTime horarioFin;
    private String diasLaborales;

    public TurnoTrabajo() {
    }

    public TurnoTrabajo(LocalTime horarioInicio, LocalTime horarioFin, String diasLaborales) {
        this.horarioInicio = horarioInicio;
        this.horarioFin = horarioFin;
        this.diasLaborales = diasLaborales;
    }

    public LocalTime getHorarioInicio() {
        return horarioInicio;
    }

    public LocalTime getHorarioFin() {
        return horarioFin;
    }

    public String getDiasLaborales() {
        return diasLaborales;
    }

}

