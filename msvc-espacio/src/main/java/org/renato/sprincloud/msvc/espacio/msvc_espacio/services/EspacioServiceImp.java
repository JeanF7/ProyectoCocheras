package org.renato.sprincloud.msvc.espacio.msvc_espacio.services;

import org.renato.sprincloud.msvc.espacio.msvc_espacio.models.entity.Espacio;
import org.renato.sprincloud.msvc.espacio.msvc_espacio.repositories.EspacioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EspacioServiceImp implements EspacioService{
    @Autowired
    private EspacioRepository repository;


    @Override
    @Transactional(readOnly = true)
    public List<Espacio> listar() {
        return (List<Espacio>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Espacio> porId(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Espacio guardar(Espacio espacio) {
        return repository.save(espacio);
    }

    @Override
    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public List<Espacio> guardarTodos(List<Espacio> espacios) {
        return (List<Espacio>) repository.saveAll(espacios);
    }

    @Override
    public List<Espacio> listarPorDisponibilidad(boolean disponibilidad) {
        return repository.findByDisponibilidad(disponibilidad);
    }

    @Override
    public List<Espacio> listarPorTipoEspacio(String tipoEspacio) {
        return repository.findByTipoEspacio(tipoEspacio);
    }

    @Override
    public List<Espacio> listarPorTarifa(double minTarifa, double maxTarifa) {
        return repository.findByTarifaBetween(minTarifa, maxTarifa);
    }

    @Override
    public List<Espacio> listarPorUbicacion(String ubicacion) {
        return repository.findByUbicacionContaining(ubicacion);
    }

}
