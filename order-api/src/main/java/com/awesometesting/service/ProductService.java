package com.awesometesting.service;

import com.awesometesting.model.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductService {
    Product saveProduct(Product product);

    List<Product> getAll();

    Optional<Product> getProduct(UUID id);
}
