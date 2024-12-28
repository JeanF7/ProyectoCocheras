package org.joseph.msvc_alquiler.models.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "detalle_alquiler")
public class DetalleAlquiler {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long espacioId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEspacioId() {
        return espacioId;
    }

    public void setEspacioId(Long espacioId) {
        this.espacioId = espacioId;
    }
}
