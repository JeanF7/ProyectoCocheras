package org.jordonez.springcloud.msvc.personal.services;

import org.jordonez.springcloud.msvc.personal.entites.Personal;
import org.jordonez.springcloud.msvc.personal.repositories.PersonalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class PersonalServiceImpl implements PersonalService{
    @Autowired
    private PersonalRepository personalRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Personal> listar() {
        return (List<Personal>) personalRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Personal> porId(Long id) {
        return personalRepository.findById(id);
    }

    @Override
    @Transactional
    public Personal guardar(Personal personal) {
        return personalRepository.save(personal);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        personalRepository.deleteById(id);
    }

    @Override
    public List<Personal> porNombre(String nombre) {
        return personalRepository.findByNombre(nombre);
    }

    @Override
    public List<Personal> porDni(String dni) {
        return personalRepository.findByDni(dni);
    }

    @Override
    public List<Personal> porRol(String rol) {
        return personalRepository.findByRol(rol);
    }

    @Override
    public List<Personal> porFechaContratacionAfter(LocalDate fecha) {
        return personalRepository.findByFechaContratacionAfter(fecha);
    }

    @Override
    public List<Personal> porTurnoHorario(LocalTime inicio, LocalTime fin) {
        return personalRepository.findByTurnoTrabajo_HorarioInicioBetween(inicio, fin);
    }

    @Override
    public List<Personal> porDiaLaboral(String dia) {
        return personalRepository.findByTurnoTrabajo_DiasLaboralesContaining(dia);
    }

    @Override
    public List<Personal> listarOrdenadosPorFechaContratacion() {
        return personalRepository.findAllByOrderByFechaContratacionDesc();
    }
}
