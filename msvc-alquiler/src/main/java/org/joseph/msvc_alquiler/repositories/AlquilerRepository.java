package org.joseph.msvc_alquiler.repositories;

import org.joseph.msvc_alquiler.models.entities.Alquiler;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface AlquilerRepository extends CrudRepository<Alquiler, Long> {
    List<Alquiler> findByIdPersonal(Long idPersonal);
    List<Alquiler> findByIdCliente(Long idCliente);
    List<Alquiler> findByEstadoAlquiler(String estado);
    List<Alquiler> findByFechaInicioBetween(LocalDate start, LocalDate end);
    List<Alquiler> findByIdEspacio(Long idEspacio);
}
