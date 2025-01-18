package org.rensex.springcloud.msvc_cliente.controllers;

import org.rensex.springcloud.msvc_cliente.models.Membresia;
import org.rensex.springcloud.msvc_cliente.models.entities.Cliente;
import org.rensex.springcloud.msvc_cliente.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/*
NUEVO
SE AGREGARON NUEVAS DIRECCIONES A LOS ENDPOINTS PARA QUE LOS MÉTODOS SOLO FUNCIONEN CON EL ROL CORRESPONDIENTE (ADMIN O USER)
 */
@RestController
@RequestMapping("/api/cliente")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @GetMapping("/authorized")
    public Map<String, String> authorized(@RequestParam String code){
        return Collections.singletonMap("code", code);
    }

    @GetMapping("/user/listar")
    public List<Cliente> listarClienteUser() {
        return clienteService.listar();
    }

    @GetMapping("/admin/listar")
    public List<Cliente> listarClienteAdmin() {
        return clienteService.listar();
    }

    @GetMapping("/admin/{id}")
    public ResponseEntity<?> detalleCliente(@PathVariable Long id) {
        Optional<Cliente> optionalCliente = clienteService.porId(id);
        return optionalCliente.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> detalleClienteUser(@PathVariable Long id) {
        Optional<Cliente> optionalCliente = clienteService.porId(id);
        return optionalCliente.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/admin/{membresia}")
    public ResponseEntity<?> crearCliente(@RequestBody Cliente cliente, @PathVariable String membresia) {
        cliente.setMembresia(new Membresia(membresia));
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.guardar(cliente));
    }

    @PostMapping("/admin/crear-varios/{membresia}")
    public ResponseEntity<?> crearVariosCliente(@RequestBody List<Cliente> clientes, @PathVariable String membresia) {
        for (int i = 0; i < clientes.size(); i++) {
            clientes.get(i).setMembresia(new Membresia(membresia));
            clienteService.guardar(clientes.get(i));
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/admin/{idCliente}")
    public ResponseEntity<?> editarCliente(@RequestBody Cliente cliente, @PathVariable Long idCliente) {
        Optional<Cliente> optionalCliente = clienteService.porId(idCliente);
        if (optionalCliente.isPresent()) {
            Cliente clienteExistente = optionalCliente.get();
            clienteExistente.setNombre(cliente.getNombre() != null ? cliente.getNombre() : clienteExistente.getNombre());
            clienteExistente.setDni(cliente.getDni() != null ? cliente.getDni() : clienteExistente.getDni());
            clienteExistente.setTelefono(cliente.getTelefono() != null ? cliente.getTelefono() : clienteExistente.getTelefono());
            clienteExistente.setEmail(cliente.getEmail() != null ? cliente.getEmail() : clienteExistente.getEmail());
            clienteExistente.setFechaRegistro(cliente.getFechaRegistro() != null ? cliente.getFechaRegistro() : clienteExistente.getFechaRegistro());
            clienteExistente.setMembresia(cliente.getMembresia() != null ? new Membresia(cliente.getMembresia().getNombreMembresia()) : clienteExistente.getMembresia());
            Cliente clienteActualizado = clienteService.guardar(clienteExistente);

            return ResponseEntity.status(HttpStatus.OK).body(clienteActualizado);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<?> eliminarCliente(@PathVariable Long id) {
        Optional<Cliente> optionalCliente = clienteService.porId(id);
        if (optionalCliente.isPresent()) {
            clienteService.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/admin/save-all")
    public ResponseEntity<?> saveAll(@RequestBody List<Cliente> clientes) {
        if (clientes == null || clientes.isEmpty()) {
            return ResponseEntity.badRequest().body("La lista de Clientes está vacía.");
        }
        List<Cliente> alquileresGuardados = clienteService.guardarTodos(clientes);
        return ResponseEntity.status(HttpStatus.CREATED).body(alquileresGuardados);
    }

    @GetMapping("/admin/buscar-por-nombre/{nombre}")
    public ResponseEntity<?> detalleCliente(@PathVariable String nombre) {
        List<Cliente> listaCliente = clienteService.porNombre(nombre);
        if (listaCliente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(listaCliente);
    }

    @GetMapping("/user/buscar-por-nombre/{nombre}")
    public ResponseEntity<?> detalleClienteUser(@PathVariable String nombre) {
        List<Cliente> listaCliente = clienteService.porNombre(nombre);
        if (listaCliente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(listaCliente);
    }

    @GetMapping("/admin/buscar-por-nombre-contiene/{keyword}")
    public ResponseEntity<?> buscarPorNombreContiene(@PathVariable String keyword) {
        List<Cliente> listaCliente = clienteService.porNombreContaining(keyword);
        if (listaCliente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(listaCliente);
    }

    @GetMapping("/user/buscar-por-nombre-contiene/{keyword}")
    public ResponseEntity<?> buscarPorNombreContieneUser(@PathVariable String keyword) {
        List<Cliente> listaCliente = clienteService.porNombreContaining(keyword);
        if (listaCliente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(listaCliente);
    }

    @GetMapping("/admin/buscar-por-dni/{dni}")
    public ResponseEntity<?> buscarPorDni(@PathVariable String dni) {
        List<Cliente> listaCliente = clienteService.porDni(dni);
        if (listaCliente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(listaCliente);
    }

    @GetMapping("/user/buscar-por-dni/{dni}")
    public ResponseEntity<?> buscarPorDniUser(@PathVariable String dni) {
        List<Cliente> listaCliente = clienteService.porDni(dni);
        if (listaCliente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(listaCliente);
    }

    @GetMapping("/admin/buscar-por-fecha-registro/{fecha}")
    public ResponseEntity<?> buscarPorFechaRegistro(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha) {
        List<Cliente> listaCliente = clienteService.porFechaRegistroAfter(fecha);
        if (listaCliente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(listaCliente);
    }

    @GetMapping("/user/buscar-por-fecha-registro/{fecha}")
    public ResponseEntity<?> buscarPorFechaRegistroUser(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha) {
        List<Cliente> listaCliente = clienteService.porFechaRegistroAfter(fecha);
        if (listaCliente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(listaCliente);
    }

    @GetMapping("/admin/buscar-por-membresia/{nombreMembresia}")
    public ResponseEntity<?> buscarPorMembresia(@PathVariable String nombreMembresia) {
        List<Cliente> listaCliente = clienteService.porMembresiaNombre(nombreMembresia);
        if (listaCliente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(listaCliente);
    }

    @GetMapping("/user/buscar-por-membresia/{nombreMembresia}")
    public ResponseEntity<?> buscarPorMembresiaUser(@PathVariable String nombreMembresia) {
        List<Cliente> listaCliente = clienteService.porMembresiaNombre(nombreMembresia);
        if (listaCliente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(listaCliente);
    }

    @GetMapping("/admin/listar-ordenados-por-fecha")
    public ResponseEntity<?> listarOrdenadosPorFecha() {
        List<Cliente> listaCliente = clienteService.listarOrdenadosPorFechaRegistroDesc();
        if (listaCliente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(listaCliente);
    }

    @GetMapping("/user/listar-ordenados-por-fecha")
    public ResponseEntity<?> listarOrdenadosPorFechaUser() {
        List<Cliente> listaCliente = clienteService.listarOrdenadosPorFechaRegistroDesc();
        if (listaCliente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(listaCliente);
    }
}
