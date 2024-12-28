package org.renato.sprincloud.msvc.espacio.msvc_espacio.services;

import org.renato.sprincloud.msvc.espacio.msvc_espacio.models.entities.Espacio;

import java.util.List;
import java.util.Optional;

public interface EspacioService {
    List<Espacio> listar();
    Optional<Espacio> porId(Long id);
    Espacio guardar(Espacio espacio);
    void eliminar(Long id);
    List<Espacio> guardarTodos(List<Espacio> espacios);

    List<Espacio> listarPorDisponibilidad(boolean disponibilidad);
    List<Espacio> listarPorTipoEspacio(String tipoEspacio);
    List<Espacio> listarPorTarifa(double minTarifa, double maxTarifa);
    List<Espacio> listarPorUbicacion(String ubicacion);

    //comunicación
    List<Espacio> espaciosPorId(Iterable<Long> ids);

}
