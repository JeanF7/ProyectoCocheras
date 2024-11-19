package org.rensex.springcloud.msvc_cliente.services;

import org.rensex.springcloud.msvc_cliente.models.entities.Cliente;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ClienteService {
    List<Cliente> listar();
    Optional<Cliente> porId(Long id);
    Cliente guardar(Cliente cliente);
    void eliminar(Long id);
    List<Cliente> guardarTodos(List<Cliente> clientes);

    List<Cliente> porNombre(String nombre);
    List<Cliente> porNombreContaining(String keyword);
    List<Cliente> porDni(String dni);
    List<Cliente> porFechaRegistroAfter(Date fecha);
    List<Cliente> porMembresiaNombre(String nombreMembresia);
    List<Cliente> listarOrdenadosPorFechaRegistroDesc();
}
