package org.joseph.msvc_alquiler.models.entities;

import jakarta.persistence.*;
import org.joseph.msvc_alquiler.models.Cliente;
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

    @Column(name = "id_cliente", nullable = false)
    private Long idCliente;

    //atributo de cliente para el caso en el que se quiera crear un cliente antes de crear o modificar un alquiler
    @Transient
    private Cliente cliente;

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

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}