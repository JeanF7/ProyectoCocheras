package org.renato.sprincloud.msvc.espacio.msvc_espacio.controllers;

import jakarta.persistence.GeneratedValue;
import org.renato.sprincloud.msvc.espacio.msvc_espacio.models.entity.Espacio;
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
            espacioBaseDatos.setDimensiones(espacio.getDimensiones());
            espacioBaseDatos.setDisponibilidad(espacio.getDisponibilidad());
            espacioBaseDatos.setTipoEspacio(espacio.getTipoEspacio());
            espacioBaseDatos.setTarifa(espacio.getTarifa());
            espacioBaseDatos.setHistorialUso(espacio.getHistorialUso());
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

}
