package org.rensex.springcloud.msvc_cliente.services;

import org.rensex.springcloud.msvc_cliente.models.entities.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteService {
    List<Cliente> listar();
    Optional<Cliente> porId(Long id);
    Cliente guardar(Cliente cliente);
    void eliminar(Long id);
}
