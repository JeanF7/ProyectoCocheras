package org.joseph.msvc_alquiler.models.entities;

import jakarta.persistence.*;
import org.joseph.msvc_alquiler.models.CondicionesAlquiler;
import org.joseph.msvc_alquiler.models.Facturacion;

import java.time.LocalDate;

@Entity
@Table(name = "alquiler")
public class Alquiler {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @JoinColumn(name = "id_reserva", nullable = false)
//    private Long iDReserva;

    private String nombreEmpleado;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String estadoAlquiler;

//    @Embedded
//    private Facturacion facturacion;
//
//    @Embedded
//    private CondicionesAlquiler condicionesAlquiler;

    @JoinColumn(name = "id_espacio", nullable = false)
    private Long idEspacio;

    @JoinColumn(name = "id_cliente", nullable = false)
    private Long idCliente;

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

    public Long getIdEspacio() {
        return idEspacio;
    }

    public void setIdEspacio(Long idEspacio) {
        this.idEspacio = idEspacio;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

}