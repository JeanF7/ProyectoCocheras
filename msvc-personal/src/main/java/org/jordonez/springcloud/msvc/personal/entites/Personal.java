package org.jordonez.springcloud.msvc.personal.entites;

import jakarta.persistence.*;
import org.jordonez.springcloud.msvc.personal.entites.models.TurnoTrabajo;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "personal")
public class Personal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String dni;
    private String telefono;
    private String email;
    private String rol;
    private LocalDate fechaContratacion;
    @Embedded
    private TurnoTrabajo turnoTrabajo;

    @ElementCollection
    @CollectionTable(name = "historial_asignaciones", joinColumns = @JoinColumn(name = "personal_id"))
    @Column(name = "id_asignacion")
    private List<Long> historialAsignaciones;  // Lista de IDs de Alquiler

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public LocalDate getFechaContratacion() {
        return fechaContratacion;
    }

    public void setFechaContratacion(LocalDate fechaContratacion) {
        this.fechaContratacion = fechaContratacion;
    }

    public TurnoTrabajo getTurnoTrabajo() {
        return turnoTrabajo;
    }

    public void setTurnoTrabajo(TurnoTrabajo turnoTrabajo) {
        this.turnoTrabajo = turnoTrabajo;
    }

    public List<Long> getHistorialAsignaciones() {
        return historialAsignaciones;
    }

    public void setHistorialAsignaciones(List<Long> historialAsignaciones) {
        this.historialAsignaciones = historialAsignaciones;
    }
}
