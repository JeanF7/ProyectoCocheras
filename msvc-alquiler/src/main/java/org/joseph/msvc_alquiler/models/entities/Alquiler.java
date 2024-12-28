package org.joseph.msvc_alquiler.models.entities;

import jakarta.persistence.*;
import org.joseph.msvc_alquiler.models.Espacio;
import org.joseph.msvc_alquiler.models.entities.DetalleAlquiler;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "alquiler")
public class Alquiler {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombreEmpleado;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String estadoAlquiler;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "alquiler_id")
    private List<DetalleAlquiler> detalleAlquilers;
    @Transient
    private List<Espacio> espacios;

    public Alquiler(){
        detalleAlquilers = new ArrayList<>();
        espacios = new ArrayList<>();
    }

    public void addDetalleAlquiler(DetalleAlquiler detalleAlquiler){
        detalleAlquilers.add(detalleAlquiler);
    }

    public void removeDetalleAlquiler(int espacioId){
        detalleAlquilers.remove(espacioId);
    }

    public Long getId() {
        return id;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getEstadoAlquiler() {
        return estadoAlquiler;
    }

    public void setEstadoAlquiler(String estadoAlquiler) {
        this.estadoAlquiler = estadoAlquiler;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<DetalleAlquiler> getDetalleAlquilers() {
        return detalleAlquilers;
    }

    public void setDetalleAlquilers(List<DetalleAlquiler> detalleAlquilers) {
        this.detalleAlquilers = detalleAlquilers;
    }

    public List<Espacio> getEspacios() {
        return espacios;
    }

    public void setEspacios(List<Espacio> espacios) {
        this.espacios = espacios;
    }
}