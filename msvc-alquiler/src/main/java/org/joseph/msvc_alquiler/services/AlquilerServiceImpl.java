package org.joseph.msvc_alquiler.services;

import org.joseph.msvc_alquiler.clients.EspacioClientRest;
import org.joseph.msvc_alquiler.models.Espacio;
import org.joseph.msvc_alquiler.models.entities.Alquiler;
import org.joseph.msvc_alquiler.models.entities.DetalleAlquiler;
import org.joseph.msvc_alquiler.repositories.AlquilerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Service
public class AlquilerServiceImpl implements AlquilerService{
    @Autowired
    private AlquilerRepository alquilerRepository;
    @Autowired
    EspacioClientRest clientRest;

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
    public void eliminar(Long id) {
        alquilerRepository.deleteById(id);
    }

    @Override
    @Transactional
    public List<Alquiler> guardarTodos(List<Alquiler> alquileres) {
        return (List<Alquiler>) alquilerRepository.saveAll(alquileres);
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

    @Override
    public Optional<Espacio> asignarEspacio(Espacio espacio, Long alquilerId) {
        Optional<Alquiler> alquilerOptional = alquilerRepository.findById(alquilerId);
        if(alquilerOptional.isPresent()){
            Espacio espacioMsvc = clientRest.detalle(espacio.getIdEspacio());

            Alquiler alquiler = alquilerOptional.get();
            DetalleAlquiler detalleAlquiler = new DetalleAlquiler();
            detalleAlquiler.setEspacioId(espacioMsvc.getIdEspacio());

            alquiler.addDetalleAlquiler(detalleAlquiler);
            alquilerRepository.save(alquiler);
            return Optional.of(espacioMsvc);

        }
        return Optional.empty();
    }

    @Override
    public Optional<Espacio> crearEspacio(Espacio espacio, Long alquilerId) {
        Optional<Alquiler> alquilerOptional = alquilerRepository.findById(alquilerId);
        if(alquilerOptional.isPresent()){
            Espacio espacioMsvc = clientRest.crear(espacio);

            Alquiler alquiler = alquilerOptional.get();
            DetalleAlquiler detalleAlquiler = new DetalleAlquiler();
            detalleAlquiler.setEspacioId(espacioMsvc.getIdEspacio());

            alquiler.addDetalleAlquiler(detalleAlquiler);
            alquilerRepository.save(alquiler);
            return Optional.of(espacioMsvc);

        }
        return Optional.empty();
    }

    @Override
    public Optional<Espacio> eliminarEspacio(Espacio espacio, Long alquilerId) {
        Optional<Alquiler> alquilerOptional = alquilerRepository.findById(alquilerId);
        if(alquilerOptional.isPresent()){
            Espacio espacioMsvc = clientRest.detalle(espacio.getIdEspacio());

            Alquiler alquiler = alquilerOptional.get();
            DetalleAlquiler detalleAlquiler = new DetalleAlquiler();
            int detalleAlquilerIndex = IntStream.range(0, alquiler.getDetalleAlquilers().size())
                    .filter(i -> alquiler.getDetalleAlquilers().get(i).getEspacioId().equals(espacioMsvc.getIdEspacio()))
                    .findFirst().orElse(-1);
            detalleAlquiler.setEspacioId(espacioMsvc.getIdEspacio());

            alquiler.removeDetalleAlquiler(detalleAlquilerIndex);
            alquilerRepository.save(alquiler);
            return Optional.of(espacioMsvc);

        }
        return Optional.empty();
    }

    @Override
    public Optional<Alquiler> porIdConEspacio(Long id) {
        Optional<Alquiler> alquilerOptional = alquilerRepository.findById(id);
        if(alquilerOptional.isPresent()){
            Alquiler alquiler = alquilerOptional.get();
            if(!alquiler.getDetalleAlquilers().isEmpty()){
                List<Long> ids = alquiler.getDetalleAlquilers().stream().
                        map(cu -> cu.getEspacioId()).
                        collect(Collectors.toList());
                List<Espacio> espacios = clientRest.espacioPorAlquiler(ids);
                alquiler.setEspacios(espacios);
            }
            return Optional.of(alquiler);

        }
        return Optional.empty();
    }

}
