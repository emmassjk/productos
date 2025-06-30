package com.productos.controllers;

import com.productos.dto.ProductosDTO;
import com.productos.servicies.ProductosServicies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/productos")
public class ProductosControllers {

    @Autowired
    private ProductosServicies service;

    @PostMapping
    public ResponseEntity<ProductosDTO> crear(@RequestBody ProductosDTO dto) {
        return ResponseEntity.ok(service.crear(dto));
    }

    @GetMapping
    public ResponseEntity<List<ProductosDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductosDTO> obtener(@PathVariable Integer id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductosDTO> actualizar(@PathVariable Integer id, @RequestBody ProductosDTO dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // METODO HATEOAS para buscar por ID
    @GetMapping("/hateoas/{id}")
    public ProductosDTO obtenerHATEOAS(@PathVariable Integer id) {
        ProductosDTO dto = service.obtenerPorId(id);

        // links urls de la misma API
        dto.add(linkTo(methodOn(ProductosControllers.class).obtenerHATEOAS(id)).withSelfRel());
        dto.add(linkTo(methodOn(ProductosControllers.class).obtenerTodosHATEOAS()).withRel("todos"));
        dto.add(linkTo(methodOn(ProductosControllers.class).eliminar(id)).withRel("eliminar"));

        // links HATEOAS para API Gateway "A mano"
        dto.add(Link.of("http://localhost:8888/api/proxy/productos/" + dto.getId()).withSelfRel());
        dto.add(Link.of("http://localhost:8888/api/proxy/productos/" + dto.getId()).withRel("Modificar HATEOAS").withType("PUT"));
        dto.add(Link.of("http://localhost:8888/api/proxy/productos/" + dto.getId()).withRel("Eliminar HATEOAS").withType("DELETE"));

        return dto;
    }

    // METODO HATEOAS para listar todos los productos utilizando HATEOAS
    @GetMapping("/hateoas")
    public List<ProductosDTO> obtenerTodosHATEOAS() {
        List<ProductosDTO> lista = service.listar();

        for (ProductosDTO dto : lista) {
            // link url de la misma API
            dto.add(linkTo(methodOn(ProductosControllers.class).obtenerHATEOAS(dto.getId())).withSelfRel());

            // links HATEOAS para API Gateway "A mano"
            dto.add(Link.of("http://localhost:8888/api/proxy/productos").withRel("Get todos HATEOAS"));
            dto.add(Link.of("http://localhost:8888/api/proxy/productos/" + dto.getId()).withRel("Crear HATEOAS").withType("POST"));
        }

        return lista;
    }
}
