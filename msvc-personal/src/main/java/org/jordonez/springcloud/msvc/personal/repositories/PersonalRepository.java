package org.jordonez.springcloud.msvc.personal.repositories;

import org.jordonez.springcloud.msvc.personal.entites.Personal;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface PersonalRepository extends CrudRepository<Personal, Long> {
    List<Personal> findByNombre(String nombre);

    List<Personal> findByDni(String dni);

    List<Personal> findByRol(String rol);

    List<Personal> findByFechaContratacionAfter(LocalDate fecha);

    List<Personal> findByTurnoTrabajo_HorarioInicioBetween(LocalTime inicio, LocalTime fin);

    List<Personal> findByTurnoTrabajo_DiasLaboralesContaining(String dia);

    List<Personal> findAllByOrderByFechaContratacionDesc();
}
