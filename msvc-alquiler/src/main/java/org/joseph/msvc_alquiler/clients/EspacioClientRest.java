package org.joseph.msvc_alquiler.clients;

import org.joseph.msvc_alquiler.models.Espacio;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "msvc-espacio", url="http://localhost:8023/api/espacios")
public interface EspacioClientRest {
    @GetMapping("{id}")
    Espacio detalle(@PathVariable Long id);
    @PostMapping
    Espacio crear(@RequestParam Espacio espacio);

    //Metodo de comunicación
    @GetMapping("/espacios-por-alquiler")
    List<Espacio> espacioPorAlquiler(@RequestParam Iterable<Long> ids);
}
