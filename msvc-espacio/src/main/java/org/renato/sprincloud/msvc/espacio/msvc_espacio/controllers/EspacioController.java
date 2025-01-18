package org.renato.sprincloud.msvc.espacio.msvc_espacio.controllers;

import org.renato.sprincloud.msvc.espacio.msvc_espacio.models.entities.Espacio;
import org.renato.sprincloud.msvc.espacio.msvc_espacio.services.EspacioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/*
NUEVO
SE AGREGARON NUEVAS DIRECCIONES A LOS ENDPOINTS PARA QUE LOS MÉTODOS SOLO FUNCIONEN CON EL ROL CORRESPONDIENTE (ADMIN O USER)
 */
@RestController
@RequestMapping("/api/espacios")
public class EspacioController {

    @Autowired
    private EspacioService service;

    @GetMapping("/authorized")
    public Map<String, String> authorized(@RequestParam String code){
        return Collections.singletonMap("code", code);
    }

    @GetMapping("/user/listar")
    public List<Espacio> listar(){
        return service.listar();
    }

    @GetMapping("/admin/listar")
    public List<Espacio> listarAdmin(){
        return service.listar();
    }

    @GetMapping("/admin/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id){
        Optional<Espacio> espacioOptional = service.porId(id);
        if(espacioOptional.isPresent()) return ResponseEntity.ok(espacioOptional);
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> detalleUser(@PathVariable Long id){
        Optional<Espacio> espacioOptional = service.porId(id);
        if(espacioOptional.isPresent()) return ResponseEntity.ok(espacioOptional);
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/admin")
    public ResponseEntity<?> crear(@RequestBody Espacio espacio){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(espacio));
    }

    @PutMapping("/admin/{id}")
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

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        Optional<Espacio> espacioOptional = service.porId(id);
        if(espacioOptional.isPresent()) {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/admin/save-all")
    public ResponseEntity<?> saveAll(@RequestBody List<Espacio> espacios) {
        if (espacios == null || espacios.isEmpty()) {
            return ResponseEntity.badRequest().body("La lista de Clientes está vacía.");
        }
        List<Espacio> alquileresGuardados = service.guardarTodos(espacios);
        return ResponseEntity.status(HttpStatus.CREATED).body(alquileresGuardados);
    }

    @GetMapping("/admin/disponibles")
    public ResponseEntity<?> listarPorDisponibilidad(@RequestParam boolean disponibilidad) {
        List<Espacio> espacios = service.listarPorDisponibilidad(disponibilidad);
        if (espacios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(espacios);
    }

    @GetMapping("/user/disponibles")
    public ResponseEntity<?> listarPorDisponibilidadUser(@RequestParam boolean disponibilidad) {
        List<Espacio> espacios = service.listarPorDisponibilidad(disponibilidad);
        if (espacios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(espacios);
    }

    @GetMapping("/admin/tipo/{tipoEspacio}")
    public ResponseEntity<?> listarPorTipoEspacio(@PathVariable String tipoEspacio) {
        List<Espacio> espacios = service.listarPorTipoEspacio(tipoEspacio);
        if (espacios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(espacios);
    }

    @GetMapping("/user/tipo/{tipoEspacio}")
    public ResponseEntity<?> listarPorTipoEspacioUser(@PathVariable String tipoEspacio) {
        List<Espacio> espacios = service.listarPorTipoEspacio(tipoEspacio);
        if (espacios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(espacios);
    }

    @GetMapping("/admin/tarifa")
    public ResponseEntity<?> listarPorTarifa(@RequestParam double minTarifa, @RequestParam double maxTarifa) {
        List<Espacio> espacios = service.listarPorTarifa(minTarifa, maxTarifa);
        if (espacios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(espacios);
    }

    @GetMapping("/user/tarifa")
    public ResponseEntity<?> listarPorTarifaUser(@RequestParam double minTarifa, @RequestParam double maxTarifa) {
        List<Espacio> espacios = service.listarPorTarifa(minTarifa, maxTarifa);
        if (espacios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(espacios);
    }

    @GetMapping("/admin/ubicacion")
    public ResponseEntity<?> listarPorUbicacion(@RequestParam String ubicacion) {
        List<Espacio> espacios = service.listarPorUbicacion(ubicacion);
        if (espacios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(espacios);
    }

    @GetMapping("/user/ubicacion")
    public ResponseEntity<?> listarPorUbicacionUser(@RequestParam String ubicacion) {
        List<Espacio> espacios = service.listarPorUbicacion(ubicacion);
        if (espacios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(espacios);
    }

    @GetMapping("/admin/espacios-por-alquiler")
    public ResponseEntity<?> espacioPorVenta(@RequestParam List<Long> ids){
        return ResponseEntity.ok(service.espaciosPorId(ids));
    }

    @GetMapping("/user/espacios-por-alquiler")
    public ResponseEntity<?> espacioPorVentaUser(@RequestParam List<Long> ids){
        return ResponseEntity.ok(service.espaciosPorId(ids));
    }
}
