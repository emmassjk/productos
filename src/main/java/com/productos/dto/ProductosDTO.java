package com.productos.dto;

import org.springframework.hateoas.RepresentationModel;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductosDTO extends RepresentationModel<ProductosDTO> {
    private Integer id;
    private String nombre;
    private String descripcion;
    private Double precioUnitario;
    private String categoria;
    private Boolean activo;
}
