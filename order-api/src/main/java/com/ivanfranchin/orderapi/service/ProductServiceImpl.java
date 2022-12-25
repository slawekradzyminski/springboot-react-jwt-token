package com.ivanfranchin.orderapi.service;

import com.ivanfranchin.orderapi.model.Product;
import com.ivanfranchin.orderapi.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

}
