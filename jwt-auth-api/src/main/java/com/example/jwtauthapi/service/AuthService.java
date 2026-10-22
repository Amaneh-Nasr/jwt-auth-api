package com.example.jwtauthapi.service;

import com.example.jwtauthapi.dto.LoginRequest;
import com.example.jwtauthapi.dto.RegisterRequest;
import com.example.jwtauthapi.model.User;
import com.example.jwtauthapi.repository.UserRepository;
import com.example.jwtauthapi.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }


    // REGISTER
    public String register(RegisterRequest request) {

        // Prüfen, ob die E-Mail-Adresse bereits existiert
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email address already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());

        // Passwort verschlüsseln
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setRole("USER");

        userRepository.save(user);

        return "User registered successfully";
    }


    // LOGIN
    public String login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Wrong password");
        }

        return jwtService.generateToken(user.getEmail());
    }
}