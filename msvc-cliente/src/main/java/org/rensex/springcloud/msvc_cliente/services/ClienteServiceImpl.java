package org.rensex.springcloud.msvc_cliente.services;

import org.rensex.springcloud.msvc_cliente.models.entities.Cliente;
import org.rensex.springcloud.msvc_cliente.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {
    @Autowired
    private ClienteRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Cliente> listar() {
        return (List<Cliente>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Cliente> porId(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Cliente guardar(Cliente cliente) {
        return repository.save(cliente);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<Cliente> porNombre(String nombre) {
        return repository.findByNombre(nombre);
    }

    @Override
    public List<Cliente> porNombreContaining(String keyword) {
        return repository.findByNombreContaining(keyword);
    }

    @Override
    public List<Cliente> porDni(String dni) {
        return repository.findByDni(dni);
    }

    @Override
    public List<Cliente> porFechaRegistroAfter(Date fecha) {
        return repository.findByFechaRegistroAfter(fecha);
    }

    @Override
    public List<Cliente> porMembresiaNombre(String nombreMembresia) {
        return repository.findByMembresia_NombreMembresia(nombreMembresia);
    }

    @Override
    public List<Cliente> listarOrdenadosPorFechaRegistroDesc() {
        return repository.findAllByOrderByFechaRegistroDesc();
    }
}
