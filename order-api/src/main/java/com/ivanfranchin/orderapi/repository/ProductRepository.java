package com.ivanfranchin.orderapi.repository;

import com.ivanfranchin.orderapi.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

}
