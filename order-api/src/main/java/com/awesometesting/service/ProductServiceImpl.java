package com.awesometesting.service;

import com.awesometesting.model.Product;
import com.awesometesting.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    @Override
    public Optional<Product> getProduct(UUID id) {
        return productRepository.findById(String.valueOf(id));
    }

}
