package org.joseph.msvc_alquiler.controllers;

import feign.FeignException;
import org.joseph.msvc_alquiler.clients.ClienteClientRest;
import org.joseph.msvc_alquiler.models.Cliente;
import feign.FeignException;
import org.joseph.msvc_alquiler.models.Espacio;
import org.joseph.msvc_alquiler.models.entities.Alquiler;
import org.joseph.msvc_alquiler.services.AlquilerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/alquiler")
public class AlquilerController {
    @Autowired
    private ClienteClientRest clienteClient;

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

    @PostMapping(value = {"/{membresia}", ""})
    public ResponseEntity<?> crearAlquiler(@RequestBody Alquiler alquiler, @PathVariable(required = false) String membresia) {
        // Validar si es un cliente existente o uno nuevo
        Cliente cliente;
        if (alquiler.getIdCliente() != null) {
            // Buscar cliente existente
            cliente = clienteClient.detalleCliente(alquiler.getIdCliente());
            if (cliente == null) {
                throw new IllegalArgumentException("Cliente no encontrado con ID: " + alquiler.getIdCliente());
            }
        } else {
            // Guardar nuevo cliente
            cliente = clienteClient.crearCliente(alquiler.getCliente(), membresia != null ? membresia : "regular");
        }

        // Asignar cliente validado al alquiler
        alquiler.getCliente().setClienteId(cliente.getClienteId());
        alquiler.getCliente().setMembresia(cliente.getMembresia());
        alquiler.setIdCliente(cliente.getClienteId());

        return ResponseEntity.status(HttpStatus.CREATED).body(alquilerService.guardar(alquiler));
    }

    @PutMapping(value = {"/{id}/{membresia}", "/{id}"})
    public ResponseEntity<?> editarAlquiler(@RequestBody Alquiler alquiler, @PathVariable Long id, @PathVariable(required = false) String membresia) {
        Optional<Alquiler> optionalAlquiler = alquilerService.porId(id);
        if (optionalAlquiler.isPresent()) {
            Alquiler alquilerExistente = optionalAlquiler.get();
            alquilerExistente.setNombreEmpleado(alquiler.getNombreEmpleado());
            alquilerExistente.setEstadoAlquiler(alquiler.getEstadoAlquiler());
            alquilerExistente.setFechaFin(alquiler.getFechaFin());
            alquilerExistente.setFechaInicio(alquiler.getFechaInicio());

            //lógica para modificar clientes
            // Validar si es un cliente existente o uno nuevo
            Cliente cliente;
            if (alquiler.getIdCliente() != null) {
                // Buscar cliente existente
                cliente = clienteClient.detalleCliente(alquiler.getIdCliente());
                if (cliente == null) {
                    throw new IllegalArgumentException("Cliente no encontrado con ID: " + alquiler.getIdCliente());
                }
            } else {
                // Guardar nuevo cliente
                cliente = clienteClient.crearCliente(alquiler.getCliente(), membresia != null ? membresia : "regular");
            }

            // Asignar cliente validado al alquiler
            alquilerExistente.getCliente().setClienteId(cliente.getClienteId());
            alquilerExistente.getCliente().setMembresia(cliente.getMembresia());
            alquilerExistente.setIdCliente(cliente.getClienteId());

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

//    @GetMapping("/empleado/{idPersonal}")
//    public ResponseEntity<?> listarPorIdEmpleado(@PathVariable Long idPersonal) {
//        List<Alquiler> alquileres = alquilerService.listarPorIdPersonal(idPersonal);
//        if (alquileres.isEmpty()) {
//            return ResponseEntity.noContent().build();
//        }
//        return ResponseEntity.ok(alquileres);
//    }

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

//    @GetMapping("/espacio/{idEspacio}")
//    public ResponseEntity<?> listarPorIdEspacio(@PathVariable Long idEspacio) {
//        List<Alquiler> alquileres = alquilerService.listarPorIdEspacio(idEspacio);
//        if (alquileres.isEmpty()) {
//            return ResponseEntity.noContent().build();
//        }
//        return ResponseEntity.ok(alquileres);
//    }

    //Métodos de la comunicación
    @PutMapping("/asignar-espacio/{alquilerId}")
    public ResponseEntity<?> asignarEspacio(@RequestBody Espacio espacio, @PathVariable Long alquilerId){
        Optional<Espacio> espacioOptional;
        try {
            espacioOptional = alquilerService.asignarEspacio(espacio, alquilerId);
        }catch (FeignException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).
                    body(Collections.singletonMap("Mensaje", "No existe el espacio " +
                            "por el id o error en la comunicación: " + e.getMessage()));
        }
        if(espacioOptional.isPresent()) return ResponseEntity.status(HttpStatus.CREATED).body(espacioOptional.get());

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/crear-espacio/{alquilerId}")
    public ResponseEntity<?> crearEspacio(@RequestBody Espacio espacio,
                                           @PathVariable Long alquilerId){
        Optional<Espacio> espacioOptional;
        try{
            espacioOptional = alquilerService.crearEspacio(espacio, alquilerId);
        }catch(FeignException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).
                    body(Collections.singletonMap("Mensaje","No se creó el espacio " +
                            "o error en la comunicación: "+e.getMessage()));
        }
        if(espacioOptional.isPresent())
            return ResponseEntity.status(HttpStatus.CREATED).body(espacioOptional.get());

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/eliminar-espacio/{alquilerId}")
    public ResponseEntity<?> eliminarProducto(@RequestBody Espacio espacio,
                                              @PathVariable Long alquilerId){
        Optional<Espacio> espacioOptional;
        try{
            espacioOptional = alquilerService.eliminarEspacio(espacio, alquilerId);
        }catch(FeignException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).
                    body(Collections.singletonMap("Mensaje","No existe el espacio " +
                            "por el id o error en la comunicación: "+e.getMessage()));
        }
        if(espacioOptional.isPresent())
            return ResponseEntity.status(HttpStatus.OK).body(espacioOptional.get());

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/detalleAlquiler/{id}")
    public ResponseEntity<?> detalleDetalleAlquiler(@PathVariable Long id){
        Optional espacioOptional = alquilerService.porIdConEspacio(id);
        if(espacioOptional.isPresent())
            return ResponseEntity.ok(espacioOptional.get());

        return ResponseEntity.notFound().build();
    }

}
