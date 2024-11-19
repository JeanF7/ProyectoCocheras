package org.renato.sprincloud.msvc.espacio.msvc_espacio.repositories;

import org.renato.sprincloud.msvc.espacio.msvc_espacio.models.entity.Espacio;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EspacioRepository extends CrudRepository<Espacio, Long> {
    List<Espacio> findByDisponibilidad(boolean disponibilidad);
    List<Espacio> findByTipoEspacio(String tipoEspacio);
    List<Espacio> findByTarifaBetween(double minTarifa, double maxTarifa);
    List<Espacio> findByUbicacionContaining(String ubicacion);
}
