package com.awesometesting.service;

import com.awesometesting.model.Product;

import java.util.List;

public interface ProductService {
    Product saveProduct(Product product);

    List<Product> getAll();
}
