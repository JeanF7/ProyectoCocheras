package org.jordonez.springcloud.msvc.personal.controllers;

import org.jordonez.springcloud.msvc.personal.entites.Personal;
import org.jordonez.springcloud.msvc.personal.services.PersonalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/personal")
public class PersonalController {
    @Autowired
    private PersonalService personalService;

    @GetMapping
    public List<Personal> listarPersonal() {
        return personalService.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detallePersonal(@PathVariable Long id) {
        Optional<Personal> optionalPersonal = personalService.porId(id);
        return optionalPersonal.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> crearPersonal(@RequestBody Personal personal) {
        Personal personal1 = personalService.guardar(personal);
        return ResponseEntity.status(HttpStatus.CREATED).body(personal1);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editarPersonal(@RequestBody Personal personal, @PathVariable Long id) {
        Optional<Personal> optionalPersonal = personalService.porId(id);
        if (optionalPersonal.isPresent()) {
            Personal personalExistente = optionalPersonal.get();
            personalExistente.setNombre(personal.getNombre());
            personalExistente.setDni(personal.getDni());
            personalExistente.setTelefono(personal.getTelefono());
            personalExistente.setEmail(personal.getEmail());
            personalExistente.setRol(personal.getRol());
            personalExistente.setFechaContratacion(personal.getFechaContratacion());
            Personal personalActualizado = personalService.guardar(personalExistente);

            return ResponseEntity.status(HttpStatus.OK).body(personalActualizado);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{idPersonal}/agregarAsignacion")
    public ResponseEntity<?> agregarAsignacion(@RequestParam Long idAsignacion, @PathVariable Long idPersonal) {
        Optional<Personal> optionalPersonal = personalService.porId(idPersonal);
        if (optionalPersonal.isPresent()) {
            optionalPersonal.get().getHistorialAsignaciones().add(idAsignacion);
            personalService.guardar(optionalPersonal.get());
            return ResponseEntity.status(HttpStatus.OK).body(idAsignacion);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{idPersonal}/eliminarAsignacion")
    public ResponseEntity<?> eliminarAsignacion(@RequestParam Long idAsignacion, @PathVariable Long idPersonal) {
        Optional<Personal> optionalPersonal = personalService.porId(idPersonal);
        if (optionalPersonal.isPresent()) {
            optionalPersonal.get().getHistorialAsignaciones().remove(idAsignacion);
            personalService.guardar(optionalPersonal.get());
            return ResponseEntity.status(HttpStatus.OK).body(idAsignacion);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarPersonal(@PathVariable Long id) {
        Optional<Personal> optionalPersonal = personalService.porId(id);
        if (optionalPersonal.isPresent()) {
            personalService.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/save-all")
    public ResponseEntity<?> saveAll(@RequestBody List<Personal> personals) {
        if (personals == null || personals.isEmpty()) {
            return ResponseEntity.badRequest().body("La lista de Clientes está vacía.");
        }
        List<Personal> personalsGuardados = personalService.guardarTodos(personals);
        return ResponseEntity.status(HttpStatus.CREATED).body(personalsGuardados);
    }

    @GetMapping("/buscar-por-nombre/{nombre}")
    public ResponseEntity<?> buscarPorNombre(@PathVariable String nombre) {
        List<Personal> listaPersonal = personalService.porNombre(nombre);
        if (listaPersonal.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(listaPersonal);
    }

    @GetMapping("/buscar-por-dni/{dni}")
    public ResponseEntity<?> buscarPorDni(@PathVariable String dni) {
        List<Personal> listaPersonal = personalService.porDni(dni);
        if (listaPersonal.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(listaPersonal);
    }

    @GetMapping("/buscar-por-rol/{rol}")
    public ResponseEntity<?> buscarPorRol(@PathVariable String rol) {
        List<Personal> listaPersonal = personalService.porRol(rol);
        if (listaPersonal.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(listaPersonal);
    }

    @GetMapping("/buscar-por-fecha-contratacion/{fecha}")
    public ResponseEntity<?> buscarPorFechaContratacion(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fecha) {
        List<Personal> listaPersonal = personalService.porFechaContratacionAfter(fecha);
        if (listaPersonal.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(listaPersonal);
    }

    @GetMapping("/buscar-por-turno-horario/{inicio}/{fin}")
    public ResponseEntity<?> buscarPorTurnoHorario(
            @PathVariable @DateTimeFormat(pattern = "HH:mm") LocalTime inicio,
            @PathVariable @DateTimeFormat(pattern = "HH:mm") LocalTime fin) {
        List<Personal> listaPersonal = personalService.porTurnoHorario(inicio, fin);
        if (listaPersonal.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(listaPersonal);
    }

    @GetMapping("/buscar-por-dia-laboral/{dia}")
    public ResponseEntity<?> buscarPorDiaLaboral(@PathVariable String dia) {
        List<Personal> listaPersonal = personalService.porDiaLaboral(dia);
        if (listaPersonal.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(listaPersonal);
    }

    @GetMapping("/listar-ordenados-por-fecha-contratacion")
    public ResponseEntity<?> listarOrdenadosPorFechaContratacion() {
        List<Personal> listaPersonal = personalService.listarOrdenadosPorFechaContratacion();
        if (listaPersonal.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(listaPersonal);
    }
}
