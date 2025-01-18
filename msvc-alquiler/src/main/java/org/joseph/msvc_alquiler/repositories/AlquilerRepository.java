package org.joseph.msvc_alquiler.repositories;

import feign.Param;
import org.joseph.msvc_alquiler.models.entities.Alquiler;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface AlquilerRepository extends CrudRepository<Alquiler, Long> {
    List<Alquiler> findByIdCliente(Long idCliente);
    List<Alquiler> findByEstadoAlquiler(String estado);
    List<Alquiler> findByFechaInicioBetween(LocalDate start, LocalDate end);
    //List<Alquiler> findByIdEspacio(Long idEspacio);

    @Modifying
    @Transactional
    @Query("DELETE FROM DetalleAlquiler ve WHERE ve.espacioId =?1")
    void eliminarDetalleAlquilerPorId(@Param("id") Long id);
}
