package org.joseph.msvc_alquiler.services;

import org.joseph.msvc_alquiler.models.entities.Alquiler;
import org.joseph.msvc_alquiler.repositories.AlquilerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Optional<Alquiler> porId(Long id) {
        return alquilerRepository.findById(id);
    }

    @Override
    public Alquiler guardar(Alquiler personal) {
        return alquilerRepository.save(personal);
    }

    @Override
    public void eliminar(Long id) {
        alquilerRepository.deleteById(id);
    }
}
