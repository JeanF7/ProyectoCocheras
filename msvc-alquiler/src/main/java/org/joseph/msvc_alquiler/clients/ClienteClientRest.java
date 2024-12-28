package org.joseph.msvc_alquiler.clients;

import org.joseph.msvc_alquiler.models.Cliente;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "msvc-cliente", url = "localhost:8022/api/cliente")
public interface ClienteClientRest {
    @GetMapping("/{id}")
    Cliente detalleCliente(@PathVariable Long id);
    @PostMapping("/{membresia}")
    Cliente crearCliente(@RequestBody Cliente cliente, @PathVariable String membresia);
    @PutMapping("/{idCliente}")
    public ResponseEntity<?> editarCliente(@RequestBody Cliente cliente, @PathVariable Long idCliente);
}
