package org.joseph.msvc_alquiler.controllers;

import org.joseph.msvc_alquiler.models.entities.Alquiler;
import org.joseph.msvc_alquiler.services.AlquilerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/alquiler")
public class AlquilerController {
    @Autowired
    private AlquilerService alquilerService;

    @GetMapping
    public List<Alquiler> listarPersonal() {
        return alquilerService.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalleAlquiler(@PathVariable Long id) {
        Optional<Alquiler> optionalPersonal = alquilerService.porId(id);
        return optionalPersonal.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> crearAlquiler(@RequestBody Alquiler alquiler) {
        Alquiler alquiler1 = alquilerService.guardar(alquiler);
        return ResponseEntity.status(HttpStatus.CREATED).body(alquiler1);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editarAlquiler(@RequestBody Alquiler alquiler, @PathVariable Long id) {
        Optional<Alquiler> optionalAlquiler = alquilerService.porId(id);
        if (optionalAlquiler.isPresent()) {
            Alquiler alquilerExistente = optionalAlquiler.get();
            alquilerExistente.setEstadoAlquiler(alquiler.getEstadoAlquiler());
            alquilerExistente.setFacturacion(alquiler.getFacturacion());
            alquilerExistente.setCondicionesAlquiler(alquiler.getCondicionesAlquiler());
            alquilerExistente.setFechaFin(alquiler.getFechaFin());
            alquilerExistente.setFechaInicio(alquiler.getFechaInicio());
            alquilerExistente.setiDReserva(alquiler.getiDReserva());
            alquilerExistente.setIdCliente(alquiler.getIdCliente());
            alquilerExistente.setIdEspacio(alquiler.getIdEspacio());
            alquilerExistente.setIdPersonal(alquiler.getIdPersonal());
            Alquiler personalActualizado = alquilerService.guardar(alquilerExistente);

            return ResponseEntity.status(HttpStatus.OK).body(personalActualizado);
        }
        return ResponseEntity.notFound().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarAlquiler(@PathVariable Long id) {
        Optional<Alquiler> optionalAlquiler = alquilerService.porId(id);
        if (optionalAlquiler.isPresent()) {
            alquilerService.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/save-all")
    public ResponseEntity<?> saveAll(@RequestBody List<Alquiler> alquileres) {
        if (alquileres == null || alquileres.isEmpty()) {
            return ResponseEntity.badRequest().body("La lista de alquileres está vacía.");
        }
        List<Alquiler> alquileresGuardados = alquilerService.guardarTodos(alquileres);
        return ResponseEntity.status(HttpStatus.CREATED).body(alquileresGuardados);
    }

    @GetMapping("/empleado/{idPersonal}")
    public ResponseEntity<?> listarPorIdEmpleado(@PathVariable Long idPersonal) {
        List<Alquiler> alquileres = alquilerService.listarPorIdPersonal(idPersonal);
        if (alquileres.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(alquileres);
    }

    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<?> listarPorIdCliente(@PathVariable Long idCliente) {
        List<Alquiler> alquileres = alquilerService.listarPorIdCliente(idCliente);
        if (alquileres.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(alquileres);
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<?> listarPorEstado(@PathVariable String estado) {
        List<Alquiler> alquileres = alquilerService.listarPorEstado(estado);
        if (alquileres.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(alquileres);
    }

    @GetMapping("/fechas")
    public ResponseEntity<?> listarPorFechas(@RequestParam LocalDate start, @RequestParam LocalDate end) {
        List<Alquiler> alquileres = alquilerService.listarPorFechas(start, end);
        if (alquileres.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(alquileres);
    }

    @GetMapping("/espacio/{idEspacio}")
    public ResponseEntity<?> listarPorIdEspacio(@PathVariable Long idEspacio) {
        List<Alquiler> alquileres = alquilerService.listarPorIdEspacio(idEspacio);
        if (alquileres.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(alquileres);
    }


}
