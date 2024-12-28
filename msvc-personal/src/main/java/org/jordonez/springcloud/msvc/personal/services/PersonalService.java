package org.jordonez.springcloud.msvc.personal.services;

import org.jordonez.springcloud.msvc.personal.entites.Personal;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface PersonalService {
    List<Personal> listar();
    Optional<Personal> porId(Long id);
    Personal guardar(Personal personal);
    void eliminar(Long id);

    //
    List<Personal> porNombre(String nombre);

    List<Personal> porDni(String dni);

    List<Personal> porRol(String rol);

    List<Personal> porFechaContratacionAfter(LocalDate fecha);

    List<Personal> porTurnoHorario(LocalTime inicio, LocalTime fin);

    List<Personal> porDiaLaboral(String dia);

    List<Personal> listarOrdenadosPorFechaContratacion();
}
