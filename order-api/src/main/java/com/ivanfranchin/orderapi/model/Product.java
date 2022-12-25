package com.ivanfranchin.orderapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    private String id;

    private String name;

    @Column(nullable = false, precision = 9, scale = 2)
    @Digits(integer = 7, fraction = 2)
    private BigDecimal price;

    private ZonedDateTime createdAt;

    public Product(String id, String name, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    @PrePersist
    public void onPrePersist() {
        createdAt = ZonedDateTime.now();
    }
}
