package com.ivanfranchin.orderapi.runner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class DatabaseInitializer implements CommandLineRunner {

    private final ProductGenerator productGenerator;
    private final UserGenerator userGenerator;

    @Override
    public void run(String... args) {
        productGenerator.generateProducts();
        userGenerator.generateUsers();
        log.info("Database initialized");
    }

}
