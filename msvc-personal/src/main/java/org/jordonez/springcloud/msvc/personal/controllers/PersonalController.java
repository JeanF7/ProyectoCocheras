package org.jordonez.springcloud.msvc.personal.controllers;

import org.jordonez.springcloud.msvc.personal.entites.Personal;
import org.jordonez.springcloud.msvc.personal.services.PersonalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
