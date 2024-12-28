package org.joseph.msvc_alquiler.services;

import org.joseph.msvc_alquiler.models.Espacio;
import org.joseph.msvc_alquiler.models.entities.Alquiler;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AlquilerService {
    List<Alquiler> listar();
    Optional<Alquiler> porId(Long id);
    Alquiler guardar(Alquiler personal);
    void eliminar(Long id);
    List<Alquiler> guardarTodos(List<Alquiler> alquileres);

//    List<Alquiler> listarPorIdPersonal(Long idPersonal);
    //List<Alquiler> listarPorIdCliente(Long idCliente);
    List<Alquiler> listarPorEstado(String estado);
    List<Alquiler> listarPorFechas(LocalDate start, LocalDate end);
    //List<Alquiler> listarPorIdEspacio(Long idEspacio);

    Optional<Espacio> asignarEspacio(Espacio espacio, Long alquilerId);
    Optional<Espacio> crearEspacio(Espacio espacio, Long alquilerId);
    Optional<Espacio> eliminarEspacio(Espacio espacio, Long alquilerId);

    Optional<Alquiler> porIdConEspacio(Long id);
}
