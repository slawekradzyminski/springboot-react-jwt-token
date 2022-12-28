package com.ivanfranchin.orderapi.runner;

import com.ivanfranchin.orderapi.model.User;
import com.ivanfranchin.orderapi.security.WebSecurityConfig;
import com.ivanfranchin.orderapi.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.IntStream;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserGenerator {

    private static final Faker FAKER = new Faker();
    @Value("${generator.numberOfUsers}")
    private final int numberOfUsers;

    private static final List<User> PREDEFINED_USERS = List.of(
            new User("admin", "admin", "Admin", "admin@mycompany.com", WebSecurityConfig.ADMIN),
            new User("user", "user", "User", "user@mycompany.com", WebSecurityConfig.USER)
    );

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public void generateUsers() {
        log.info("Start generating users");
        PREDEFINED_USERS.forEach(this::saveUser);
        IntStream.range(0, numberOfUsers)
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
