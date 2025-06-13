package com.productos.repositories;

import com.productos.models.ProductosModels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductosRepositories extends JpaRepository<ProductosModels, Integer> {
}
