package org.renato.sprincloud.msvc.espacio.msvc_espacio.models.entity;

import jakarta.persistence.*;
import org.renato.sprincloud.msvc.espacio.msvc_espacio.models.HistorialUso;

@Entity
@Table(name = "espacios")
public class Espacio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idEspacio;
    private String ubicacion;
    private double dimensiones;
    private boolean disponibilidad;
    private String tipoEspacio;
    private double tarifa;
    @Embedded
    private HistorialUso historialUso;

    public long getIdEspacio() {
        return idEspacio;
    }

    public void setIdEspacio(long idEspacio) {
        this.idEspacio = idEspacio;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public double getDimensiones() {
        return dimensiones;
    }

    public void setDimensiones(double dimensiones) {
        this.dimensiones = dimensiones;
    }

    public boolean getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(boolean disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public String getTipoEspacio() {
        return tipoEspacio;
    }

    public void setTipoEspacio(String tipoEspacio) {
        this.tipoEspacio = tipoEspacio;
    }

    public double getTarifa() {
        return tarifa;
    }

    public void setTarifa(double tarifa) {
        this.tarifa = tarifa;
    }

    public HistorialUso getHistorialUso() {
        return historialUso;
    }

    public void setHistorialUso(HistorialUso historialUso) {
        this.historialUso = historialUso;
    }

}
