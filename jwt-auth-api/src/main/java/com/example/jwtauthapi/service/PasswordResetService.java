package com.example.jwtauthapi.service;

import com.example.jwtauthapi.model.PasswordResetToken;
import com.example.jwtauthapi.model.User;
import com.example.jwtauthapi.repository.PasswordResetTokenRepository;
import com.example.jwtauthapi.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;


@Service
public class PasswordResetService {


    private final UserRepository userRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;


    public PasswordResetService(
            UserRepository userRepository,
            PasswordResetTokenRepository tokenRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
    }



    // Passwort vergessen
    public String createResetToken(String email) {


        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));



        String token = UUID.randomUUID().toString();



        PasswordResetToken resetToken =
                new PasswordResetToken();


        resetToken.setToken(token);

        resetToken.setUser(user);

        resetToken.setExpiryDate(
                LocalDateTime.now().plusMinutes(15)
        );


        tokenRepository.save(resetToken);



        return token;
    }




    // Neues Passwort setzen
    public void resetPassword(
            String token,
            String newPassword) {


        PasswordResetToken resetToken =
                tokenRepository.findByToken(token)
                        .orElseThrow(() ->
                                new RuntimeException("Invalid token"));



        if(resetToken.getExpiryDate()
                .isBefore(LocalDateTime.now())) {


            throw new RuntimeException("Token expired");
        }



        User user = resetToken.getUser();



        user.setPassword(
                passwordEncoder.encode(newPassword)
        );


        userRepository.save(user);



        // Token nach Benutzung löschen
        tokenRepository.delete(resetToken);
    }
}