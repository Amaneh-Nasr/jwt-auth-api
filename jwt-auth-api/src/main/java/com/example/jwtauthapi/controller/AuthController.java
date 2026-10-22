package com.example.jwtauthapi.controller;


import com.example.jwtauthapi.dto.ForgotPasswordRequest;
import com.example.jwtauthapi.dto.LoginRequest;
import com.example.jwtauthapi.dto.RegisterRequest;
import com.example.jwtauthapi.dto.ResetPasswordRequest;
import com.example.jwtauthapi.service.AuthService;
import com.example.jwtauthapi.service.PasswordResetService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping("/auth")
public class AuthController {


    private final AuthService authService;

    private final PasswordResetService passwordResetService;



    public AuthController(
            AuthService authService,
            PasswordResetService passwordResetService) {

        this.authService = authService;
        this.passwordResetService = passwordResetService;
    }





    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestBody RegisterRequest request) {

        try {
            String result = authService.register(request);
            return ResponseEntity.ok(result);

        } catch (RuntimeException e) {
            if ("Email address already exists".equals(e.getMessage())) {
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body("Email address already exists");
            }

            throw e;
        }
    }





    @PostMapping("/login")
    public String login(
            @RequestBody LoginRequest request) {

        return authService.login(request);
    }





    @GetMapping("/test")
    public String test() {

        return "JWT Filter funktioniert!";
    }





    @PostMapping("/forgot-password")
    public String forgotPassword(
            @RequestBody ForgotPasswordRequest request) {


        String token =
                passwordResetService.createResetToken(
                        request.getEmail()
                );


        return token;
    }






    @PostMapping("/reset-password")
    public String resetPassword(
            @RequestBody ResetPasswordRequest request) {


        passwordResetService.resetPassword(
                request.getToken(),
                request.getNewPassword()
        );


        return "Password changed successfully";
    }

}