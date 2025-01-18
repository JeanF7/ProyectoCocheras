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
import java.util.Map;
import java.util.Optional;

/*
NUEVO
SE AGREGARON NUEVAS DIRECCIONES A LOS ENDPOINTS PARA QUE LOS MÉTODOS SOLO FUNCIONEN CON EL ROL CORRESPONDIENTE (ADMIN O USER)
 */
@RestController
@RequestMapping("/api/alquiler")
public class AlquilerController {
    @Autowired
    private ClienteClientRest clienteClient;

    @Autowired
    private AlquilerService alquilerService;

    @GetMapping("/authorized")
    public Map<String, String> authorized(@RequestParam String code){
        return Collections.singletonMap("code", code);
    }

    @GetMapping("/user/listar")
    public List<Alquiler> listarPersonal() {
        return alquilerService.listar();
    }

    @GetMapping("/admin/listar")
    public List<Alquiler> listarPersonalAdmin() {
        return alquilerService.listar();
    }

    @GetMapping("/admin/{id}")
    public ResponseEntity<?> detalleAlquiler(@PathVariable Long id) {
        Optional<Alquiler> optionalPersonal = alquilerService.porId(id);
        return optionalPersonal.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> detalleAlquilerUser(@PathVariable Long id) {
        Optional<Alquiler> optionalPersonal = alquilerService.porId(id);
        return optionalPersonal.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /*
    NUEVO
    se mejoró la lógica de todo el proyecto, finalmente se realizaron pruebas y corroboraron su funcionamiento
    */
    @PostMapping(value = {"/admin/{membresia}", "/admin"})
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

    @PutMapping(value = {"/admin/{id}/{membresia}", "/admin/{id}"})
    public ResponseEntity<?> editarAlquiler(@RequestBody Alquiler alquiler, @PathVariable Long id, @PathVariable(required = false) String membresia) {
        Optional<Alquiler> optionalAlquiler = alquilerService.porId(id);
        if (optionalAlquiler.isPresent()) {
            Alquiler alquilerExistente = optionalAlquiler.get();
            alquilerExistente.setNombreEmpleado(alquiler.getNombreEmpleado());
            alquilerExistente.setEstadoAlquiler(alquiler.getEstadoAlquiler());
            alquilerExistente.setFechaFin(alquiler.getFechaFin());
            alquilerExistente.setFechaInicio(alquiler.getFechaInicio());

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


    @DeleteMapping("/admin/{id}")
    public ResponseEntity<?> eliminarAlquiler(@PathVariable Long id) {
        Optional<Alquiler> optionalAlquiler = alquilerService.porId(id);
        if (optionalAlquiler.isPresent()) {
            alquilerService.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/admin/save-all")
    public ResponseEntity<?> saveAll(@RequestBody List<Alquiler> alquileres) {
        if (alquileres == null || alquileres.isEmpty()) {
            return ResponseEntity.badRequest().body("La lista de alquileres está vacía.");
        }
        List<Alquiler> alquileresGuardados = alquilerService.guardarTodos(alquileres);
        return ResponseEntity.status(HttpStatus.CREATED).body(alquileresGuardados);
    }

    @GetMapping("/admin/cliente/{idCliente}")
    public ResponseEntity<?> listarPorIdCliente(@PathVariable Long idCliente) {
        List<Alquiler> alquileres = alquilerService.listarPorIdCliente(idCliente);
        if (alquileres.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(alquileres);
    }

    @GetMapping("/user/cliente/{idCliente}")
    public ResponseEntity<?> listarPorIdClienteUser(@PathVariable Long idCliente) {
        List<Alquiler> alquileres = alquilerService.listarPorIdCliente(idCliente);
        if (alquileres.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(alquileres);
    }

    @GetMapping("/admin/estado/{estado}")
    public ResponseEntity<?> listarPorEstado(@PathVariable String estado) {
        List<Alquiler> alquileres = alquilerService.listarPorEstado(estado);
        if (alquileres.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(alquileres);
    }

    @GetMapping("/user/estado/{estado}")
    public ResponseEntity<?> listarPorEstadoUser(@PathVariable String estado) {
        List<Alquiler> alquileres = alquilerService.listarPorEstado(estado);
        if (alquileres.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(alquileres);
    }

    @GetMapping("/admin/fechas")
    public ResponseEntity<?> listarPorFechas(@RequestParam LocalDate start, @RequestParam LocalDate end) {
        List<Alquiler> alquileres = alquilerService.listarPorFechas(start, end);
        if (alquileres.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(alquileres);
    }

    @GetMapping("/user/fechas")
    public ResponseEntity<?> listarPorFechasUser(@RequestParam LocalDate start, @RequestParam LocalDate end) {
        List<Alquiler> alquileres = alquilerService.listarPorFechas(start, end);
        if (alquileres.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(alquileres);
    }

    //Métodos de la comunicación
    @PutMapping("/admin/asignar-espacio/{alquilerId}")
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

    @PostMapping("/admin/crear-espacio/{alquilerId}")
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

    @DeleteMapping("/admin/eliminar-espacio/{alquilerId}")
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

    @GetMapping("/admin/detalleAlquiler/{id}")
    public ResponseEntity<?> detalleDetalleAlquiler(@PathVariable Long id){
        Optional espacioOptional = alquilerService.porIdConEspacio(id);
        if(espacioOptional.isPresent())
            return ResponseEntity.ok(espacioOptional.get());

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/user/detalleAlquiler/{id}")
    public ResponseEntity<?> detalleDetalleAlquilerUser(@PathVariable Long id){
        Optional espacioOptional = alquilerService.porIdConEspacio(id);
        if(espacioOptional.isPresent())
            return ResponseEntity.ok(espacioOptional.get());

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/admin/eliminar-detalleAlquiler/{id}")
    public ResponseEntity<?> eliminarDetalleAlquilerPorId(@PathVariable Long id){
        alquilerService.eliminarDetalleAlquilerPorId(id);
        return ResponseEntity.noContent().build();
    }

}
