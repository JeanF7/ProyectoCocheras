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
}
