package org.renato.sprincloud.msvc.espacio.msvc_espacio.controllers;

import org.renato.sprincloud.msvc.espacio.msvc_espacio.models.entities.Espacio;
import org.renato.sprincloud.msvc.espacio.msvc_espacio.services.EspacioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/espacios")
public class EspacioController {

    @Autowired
    private EspacioService service;

    @GetMapping
    public List<Espacio> listar(){
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id){
        Optional<Espacio> espacioOptional = service.porId(id);
        if(espacioOptional.isPresent()) return ResponseEntity.ok(espacioOptional);
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Espacio espacio){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(espacio));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@RequestBody Espacio espacio, @PathVariable Long id){
        Optional<Espacio> espacioOptional = service.porId(id);
        if(espacioOptional.isPresent()) {
            Espacio espacioBaseDatos = espacioOptional.get();
            espacioBaseDatos.setUbicacion(espacio.getUbicacion());
            espacioBaseDatos.setDisponibilidad(espacio.getDisponibilidad());
            espacioBaseDatos.setTipoEspacio(espacio.getTipoEspacio());
            espacioBaseDatos.setTarifa(espacio.getTarifa());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(espacioBaseDatos));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        Optional<Espacio> espacioOptional = service.porId(id);
        if(espacioOptional.isPresent()) {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/save-all")
    public ResponseEntity<?> saveAll(@RequestBody List<Espacio> espacios) {
        if (espacios == null || espacios.isEmpty()) {
            return ResponseEntity.badRequest().body("La lista de Clientes está vacía.");
        }
        List<Espacio> alquileresGuardados = service.guardarTodos(espacios);
        return ResponseEntity.status(HttpStatus.CREATED).body(alquileresGuardados);
    }

    @GetMapping("/disponibles")
    public ResponseEntity<?> listarPorDisponibilidad(@RequestParam boolean disponibilidad) {
        List<Espacio> espacios = service.listarPorDisponibilidad(disponibilidad);
        if (espacios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(espacios);
    }

    @GetMapping("/tipo/{tipoEspacio}")
    public ResponseEntity<?> listarPorTipoEspacio(@PathVariable String tipoEspacio) {
        List<Espacio> espacios = service.listarPorTipoEspacio(tipoEspacio);
        if (espacios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(espacios);
    }

    @GetMapping("/tarifa")
    public ResponseEntity<?> listarPorTarifa(@RequestParam double minTarifa, @RequestParam double maxTarifa) {
        List<Espacio> espacios = service.listarPorTarifa(minTarifa, maxTarifa);
        if (espacios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(espacios);
    }

    @GetMapping("/ubicacion")
    public ResponseEntity<?> listarPorUbicacion(@RequestParam String ubicacion) {
        List<Espacio> espacios = service.listarPorUbicacion(ubicacion);
        if (espacios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(espacios);
    }

    @GetMapping("/espacios-por-alquiler")
    public ResponseEntity<?> espacioPorVenta(@RequestParam List<Long> ids){
        return ResponseEntity.ok(service.espaciosPorId(ids));
    }


}
