package org.jordonez.springcloud.msvc.personal.services;

import org.jordonez.springcloud.msvc.personal.entites.Personal;

import java.util.List;
import java.util.Optional;

public interface PersonalService {
    List<Personal> listar();
    Optional<Personal> porId(Long id);
    Personal guardar(Personal personal);
    void eliminar(Long id);
}
