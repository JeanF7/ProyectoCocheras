package org.rensex.springcloud.msvc_cliente.controllers;

import org.rensex.springcloud.msvc_cliente.models.Membresia;
import org.rensex.springcloud.msvc_cliente.models.entities.Cliente;
import org.rensex.springcloud.msvc_cliente.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cliente")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public List<Cliente> listarClientes() {
        return clienteService.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalleCliente(@PathVariable Long id) {
        Optional<Cliente> optionalCliente = clienteService.porId(id);
        return optionalCliente.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("{membresia}")
    public ResponseEntity<?> crearCliente(@RequestBody Cliente cliente, @PathVariable String membresia) {
        cliente.setMembresia(new Membresia(membresia));
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.guardar(cliente));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editarCliente(@RequestBody Cliente cliente, @PathVariable Long id) {
        Optional<Cliente> optionalCliente = clienteService.porId(id);
        if (optionalCliente.isPresent()) {
            Cliente clienteExistente = optionalCliente.get();
            clienteExistente.setNombre(cliente.getNombre() != null ? cliente.getNombre() : clienteExistente.getNombre());
            clienteExistente.setDni(cliente.getDni() != null ? cliente.getDni() : clienteExistente.getDni());
            clienteExistente.setTelefono(cliente.getTelefono() != null ? cliente.getTelefono() : clienteExistente.getTelefono());
            clienteExistente.setEmail(cliente.getEmail() != null ? cliente.getEmail() : clienteExistente.getEmail());
            clienteExistente.setFechaRegistro(cliente.getFechaRegistro() != null ? cliente.getFechaRegistro() : clienteExistente.getFechaRegistro());
            clienteExistente.setMembresia(cliente.getMembresia() != null ? cliente.getMembresia() : clienteExistente.getMembresia());
            Cliente clienteActualizado = clienteService.guardar(clienteExistente);

            return ResponseEntity.status(HttpStatus.OK).body(clienteActualizado);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarCliente(@PathVariable Long id) {
        Optional<Cliente> optionalCliente = clienteService.porId(id);
        if (optionalCliente.isPresent()) {
            clienteService.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{idCliente}/agregarAlquiler")
    public ResponseEntity<?> agregarAlquiler(@RequestParam Long idAlquiler, @PathVariable Long idCliente) {
        Optional<Cliente> optionalCliente = clienteService.porId(idCliente);
        if (optionalCliente.isPresent()) {
            //optionalCliente.get().getHistorialAlquileres().add(idAlquiler);
            clienteService.guardar(optionalCliente.get());
            return ResponseEntity.status(HttpStatus.OK).body(idAlquiler);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{idCliente}/eliminarAlquiler")
    public ResponseEntity<?> eliminarAlquiler(@RequestParam Long idAlquiler, @PathVariable Long idCliente) {
        Optional<Cliente> optionalCliente = clienteService.porId(idCliente);
        if (optionalCliente.isPresent()) {
            //optionalCliente.get().getHistorialAlquileres().remove(idAlquiler);
            clienteService.guardar(optionalCliente.get());
            return ResponseEntity.status(HttpStatus.OK).body(idAlquiler);
        }
        return ResponseEntity.notFound().build();
    }
}
