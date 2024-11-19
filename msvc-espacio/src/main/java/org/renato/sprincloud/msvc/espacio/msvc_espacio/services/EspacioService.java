package org.renato.sprincloud.msvc.espacio.msvc_espacio.services;

import org.renato.sprincloud.msvc.espacio.msvc_espacio.models.entity.Espacio;

import java.util.List;
import java.util.Optional;

public interface EspacioService {
    List<Espacio> listar();
    Optional<Espacio> porId(Long id);
    Espacio guardar(Espacio espacio);
    void eliminar(Long id);
}
