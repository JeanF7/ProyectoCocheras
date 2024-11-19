package org.joseph.msvc_alquiler.services;

import org.joseph.msvc_alquiler.models.entities.Alquiler;
import org.joseph.msvc_alquiler.repositories.AlquilerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class AlquilerServiceImpl implements AlquilerService{
    @Autowired
    private AlquilerRepository alquilerRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Alquiler> listar() {
        return (List<Alquiler>) alquilerRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Alquiler> porId(Long id) {
        return alquilerRepository.findById(id);
    }

    @Override
    @Transactional
    public Alquiler guardar(Alquiler personal) {
        return alquilerRepository.save(personal);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        alquilerRepository.deleteById(id);
    }

    @Override
    @Transactional
    public List<Alquiler> guardarTodos(List<Alquiler> alquileres) {
        return (List<Alquiler>) alquilerRepository.saveAll(alquileres);
    }

    @Override
    public List<Alquiler> listarPorIdPersonal(Long idPersonal) {
        return alquilerRepository.findByIdPersonal(idPersonal);
    }

    @Override
    public List<Alquiler> listarPorIdCliente(Long idCliente) {
        return alquilerRepository.findByIdCliente(idCliente);
    }

    @Override
    public List<Alquiler> listarPorEstado(String estado) {
        return alquilerRepository.findByEstadoAlquiler(estado);
    }

    @Override
    public List<Alquiler> listarPorFechas(LocalDate start, LocalDate end) {
        return alquilerRepository.findByFechaInicioBetween(start, end);
    }

    @Override
    public List<Alquiler> listarPorIdEspacio(Long idEspacio) {
        return alquilerRepository.findByIdEspacio(idEspacio);
    }

}
