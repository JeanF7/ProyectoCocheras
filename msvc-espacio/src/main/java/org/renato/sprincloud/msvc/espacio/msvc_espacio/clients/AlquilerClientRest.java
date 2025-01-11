package org.renato.sprincloud.msvc.espacio.msvc_espacio.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="msvc-alquiler", url = "http://localhost:8029/api/alquiler")
public interface AlquilerClientRest {
    @DeleteMapping("/eliminar-ventaProducto/{id}")
    void eliminarDetalleAlquilerPorId(@PathVariable Long id);
}
