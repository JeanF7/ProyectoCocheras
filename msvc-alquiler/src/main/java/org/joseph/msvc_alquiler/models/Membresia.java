package org.joseph.msvc_alquiler.models;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;

import java.util.ArrayList;
import java.util.List;

@Embeddable
public class Membresia {
    private String nombreMembresia;
    private double descuento;

    @ElementCollection
    private List<String> beneficios;

    public Membresia() {
        this.beneficios = new ArrayList<>();
    }

    public Membresia(String nombreMembresia) {
        this.beneficios = new ArrayList<>();
        this.nombreMembresia = nombreMembresia;
        inicializarMembresia(nombreMembresia.toLowerCase());
    }

    private void inicializarMembresia(String nombreMembresia) {
        switch (nombreMembresia) {
            case "gold":
                this.descuento = 0.9;
                this.beneficios.add("Acceso a eventos especiales");
                this.beneficios.add("Descuento en productos");
                break;
            case "premium":
                this.descuento = 0.8;
                this.beneficios.add("Acceso VIP a eventos");
                this.beneficios.add("Descuento en productos y servicios");
                this.beneficios.add("Atención personalizada");
                break;
            default:
                this.descuento = 1;
                this.beneficios.add("Acceso básico a servicios");
        }
    }

    public String getNombreMembresia() {
        return nombreMembresia;
    }

    public void setNombreMembresia(String nombreMembresia) {
        this.nombreMembresia = nombreMembresia;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public List<String> getBeneficios() {
        return beneficios;
    }

    public void setBeneficios(List<String> beneficios) {
        this.beneficios = beneficios;
    }
}
