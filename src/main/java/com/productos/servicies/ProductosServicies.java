package com.productos.servicies;

import com.productos.models.ProductosModels;
import com.productos.dto.ProductosDTO;
import com.productos.repositories.ProductosRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductosServicies {

    @Autowired
    private ProductosRepositories productosRepository;

    private ProductosDTO toDTO(ProductosModels producto) {
        return new ProductosDTO(
                producto.getId(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getPrecioUnitario(),
                producto.getCategoria(),
                producto.getActivo()
        );
    }

    private ProductosModels toEntity(ProductosDTO dto) {
        ProductosModels producto = new ProductosModels();
        producto.setId(dto.getId());
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecioUnitario(dto.getPrecioUnitario());
        producto.setCategoria(dto.getCategoria());
        producto.setActivo(dto.getActivo());
        return producto;
    }

    public ProductosDTO crear(ProductosDTO dto) {
        ProductosModels producto = toEntity(dto);
        return toDTO(productosRepository.save(producto));
    }

    public List<ProductosDTO> listar() {
        return productosRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ProductosDTO obtenerPorId(Integer id) {
        ProductosModels producto = productosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return toDTO(producto);
    }

    public ProductosDTO actualizar(Integer id, ProductosDTO dto) {
        ProductosModels existente = productosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        existente.setNombre(dto.getNombre());
        existente.setDescripcion(dto.getDescripcion());
        existente.setPrecioUnitario(dto.getPrecioUnitario());
        existente.setCategoria(dto.getCategoria());
        existente.setActivo(dto.getActivo());

        return toDTO(productosRepository.save(existente));
    }

    public void eliminar(Integer id) {
        productosRepository.deleteById(id);
    }
}
