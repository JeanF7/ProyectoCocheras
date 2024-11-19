package org.joseph.msvc_alquiler.services;

import org.joseph.msvc_alquiler.models.entities.Alquiler;

import java.util.List;
import java.util.Optional;

public interface AlquilerService {
    List<Alquiler> listar();
    Optional<Alquiler> porId(Long id);
    Alquiler guardar(Alquiler personal);
    void eliminar(Long id);
}
