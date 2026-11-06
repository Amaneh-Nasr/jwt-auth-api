package com.example.jwtauthapi.controller;

import com.example.jwtauthapi.model.User;
import com.example.jwtauthapi.repository.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@io.swagger.v3.oas.annotations.security.SecurityRequirement(
        name = "Bearer Authentication"
)
public class TestController {

    private final UserRepository userRepository;

    public TestController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/test")
    public String test() {
        return "Test erfolgreich";
    }

    @PreAuthorize("hasRole('USER')")
    // Nur angemeldete Benutzer mit der Rolle USER dürfen diesen Endpunkt aufrufen.
    @GetMapping("/user/profile")
    public String profile() {
        return "User Profile";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/users")
    public List<User> getUsers() {
        return userRepository.findAll();
    }
}