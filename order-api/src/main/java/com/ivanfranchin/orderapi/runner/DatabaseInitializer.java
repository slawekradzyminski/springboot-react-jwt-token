package com.ivanfranchin.orderapi.runner;

import com.ivanfranchin.orderapi.model.Product;
import com.ivanfranchin.orderapi.model.User;
import com.ivanfranchin.orderapi.security.WebSecurityConfig;
import com.ivanfranchin.orderapi.service.ProductService;
import com.ivanfranchin.orderapi.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

@Slf4j
@RequiredArgsConstructor
@Component
public class DatabaseInitializer implements CommandLineRunner {

    private static final Faker FAKER = new Faker();
    private static final int NUMBER_OF_USERS = 20;
    private static final int NUMBER_OF_PRODUCTS = 100;

    private static final List<User> PREDEFINED_USERS = List.of(
            new User("admin", "admin", "Admin", "admin@mycompany.com", WebSecurityConfig.ADMIN),
            new User("user", "user", "User", "user@mycompany.com", WebSecurityConfig.USER)
    );

    private static final List<Product> PREDEFINED_PRODUCTS = List.of(
            new Product("e6251501-fa8f-40f1-8f2b-ea944c0e524f", "iPhone X", BigDecimal.TEN),
            new Product("849ed037-5776-4de5-97ae-974ca6da3d62", "Macbook Pro 15'", BigDecimal.valueOf(50.15))
    );

    private final UserService userService;

    private final ProductService productService;
    private final PasswordEncoder passwordEncoder;

    private static Product getRandomProduct() {
        return new Product(
                UUID.randomUUID().toString(),
                FAKER.commerce().productName(),
                new BigDecimal(FAKER.commerce().price())
        );
    }

    @Override
    public void run(String... args) {
        if (!userService.getUsers().isEmpty()) {
            return;
        }
        generateUsers();
        generateProducts();
        log.info("Database initialized");
    }

    private void generateProducts() {
        log.info("Start generating products");
        PREDEFINED_PRODUCTS.forEach(productService::saveProduct);
        IntStream.range(0, NUMBER_OF_PRODUCTS)
                .mapToObj(i -> getRandomProduct())
                .forEach(productService::saveProduct);
        log.info("Finish generating products");
    }

    private void generateUsers() {
        log.info("Start generating users");
        PREDEFINED_USERS.forEach(this::saveUser);
        IntStream.range(0, NUMBER_OF_USERS)
                .mapToObj(i -> getRandomUser())
                .forEach(this::saveUser);
        log.info("Finish generating users");
    }

    private User getRandomUser() {
        return new User(
                FAKER.name().username(),
                FAKER.internet().password(),
                FAKER.name().firstName(),
                FAKER.internet().emailAddress(),
                WebSecurityConfig.USER
        );
    }

    private void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveUser(user);
    }

}
