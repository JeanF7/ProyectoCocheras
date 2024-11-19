package org.rensex.springcloud.msvc_cliente.repositories;

import org.rensex.springcloud.msvc_cliente.models.entities.Cliente;
import org.springframework.data.repository.CrudRepository;

public interface ClienteRepository extends CrudRepository<Cliente, Long> {
}
