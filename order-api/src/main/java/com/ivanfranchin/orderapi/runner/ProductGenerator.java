package com.ivanfranchin.orderapi.runner;

import com.ivanfranchin.orderapi.model.Product;
import com.ivanfranchin.orderapi.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductGenerator {

    private static final Faker FAKER = new Faker();
    @Value("${generator.numberOfProducts}")
    private final int numberOfProducts;

    private static final List<Product> PREDEFINED_PRODUCTS = List.of(
            new Product("e6251501-fa8f-40f1-8f2b-ea944c0e524f", "iPhone X", BigDecimal.TEN),
            new Product("849ed037-5776-4de5-97ae-974ca6da3d62", "Macbook Pro 15'", BigDecimal.valueOf(50.15))
    );

    private final ProductService productService;

    private static Product getRandomProduct() {
        return new Product(
                UUID.randomUUID().toString(),
                FAKER.commerce().productName(),
                new BigDecimal(FAKER.commerce().price())
        );
    }

    public void generateProducts() {
        log.info("Start generating products");
        PREDEFINED_PRODUCTS.forEach(productService::saveProduct);
        IntStream.range(0, numberOfProducts)
                .mapToObj(i -> getRandomProduct())
                .forEach(productService::saveProduct);
        log.info("Finish generating products");
    }

}
