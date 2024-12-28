package org.rensex.springcloud.msvc_cliente.controllers;

import org.rensex.springcloud.msvc_cliente.models.Membresia;
import org.rensex.springcloud.msvc_cliente.models.entities.Cliente;
import org.rensex.springcloud.msvc_cliente.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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

    @PostMapping("crear-varios/{membresia}")
    public ResponseEntity<?> crearVariosCliente(@RequestBody List<Cliente> clientes, @PathVariable String membresia) {
        for (int i = 0; i < clientes.size(); i++) {
            clientes.get(i).setMembresia(new Membresia(membresia));
            clienteService.guardar(clientes.get(i));
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
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

    //métodos de filtrado:
    @GetMapping("/buscar    -por-nombre/{nombre}")
    public ResponseEntity<?> detalleCliente(@PathVariable String nombre) {
        List<Cliente> listaCliente = clienteService.porNombre(nombre);
        if (listaCliente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(listaCliente);
    }

    @GetMapping("/buscar-por-nombre-contiene/{keyword}")
    public ResponseEntity<?> buscarPorNombreContiene(@PathVariable String keyword) {
        List<Cliente> listaCliente = clienteService.porNombreContaining(keyword);
        if (listaCliente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(listaCliente);
    }

    @GetMapping("/buscar-por-dni/{dni}")
    public ResponseEntity<?> buscarPorDni(@PathVariable String dni) {
        List<Cliente> listaCliente = clienteService.porDni(dni);
        if (listaCliente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(listaCliente);
    }

    @GetMapping("/buscar-por-fecha-registro/{fecha}")
    public ResponseEntity<?> buscarPorFechaRegistro(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha) {
        List<Cliente> listaCliente = clienteService.porFechaRegistroAfter(fecha);
        if (listaCliente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(listaCliente);
    }

    @GetMapping("/buscar-por-membresia/{nombreMembresia}")
    public ResponseEntity<?> buscarPorMembresia(@PathVariable String nombreMembresia) {
        List<Cliente> listaCliente = clienteService.porMembresiaNombre(nombreMembresia);
        if (listaCliente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(listaCliente);
    }

    @GetMapping("/listar-ordenados-por-fecha")
    public ResponseEntity<?> listarOrdenadosPorFecha() {
        List<Cliente> listaCliente = clienteService.listarOrdenadosPorFechaRegistroDesc();
        if (listaCliente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(listaCliente);
    }
}
