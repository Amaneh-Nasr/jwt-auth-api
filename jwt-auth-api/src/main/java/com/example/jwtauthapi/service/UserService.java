package com.example.jwtauthapi.service;

import com.example.jwtauthapi.dto.ChangePasswordRequest;
import com.example.jwtauthapi.model.User;
import com.example.jwtauthapi.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public void changePassword(
            String email,
            ChangePasswordRequest request) {


        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));


        // altes Passwort prüfen
        if (!passwordEncoder.matches(
                request.getOldPassword(),
                user.getPassword())) {

            throw new RuntimeException("Old password incorrect");
        }


        // neues Passwort verschlüsseln
        user.setPassword(
                passwordEncoder.encode(
                        request.getNewPassword()
                )
        );


        userRepository.save(user);
    }
}