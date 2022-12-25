package com.ivanfranchin.orderapi.service;

import com.ivanfranchin.orderapi.model.Product;

import java.util.List;

public interface ProductService {
    Product saveProduct(Product product);

    List<Product> getAll();
}
