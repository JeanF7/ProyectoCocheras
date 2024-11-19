package org.rensex.springcloud.msvc_cliente.repositories;

import org.rensex.springcloud.msvc_cliente.models.entities.Cliente;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface ClienteRepository extends CrudRepository<Cliente, Long> {
    List<Cliente> findByNombre(String nombre);

    List<Cliente> findByNombreContaining(String keyword);

    List<Cliente> findByDni(String dni);

    List<Cliente> findByFechaRegistroAfter(Date fecha);

    List<Cliente> findByMembresia_NombreMembresia(String nombreMembresia);

    List<Cliente> findAllByOrderByFechaRegistroDesc();
}
