package com.example.jwtauthapi.controller;


import com.example.jwtauthapi.dto.ChangePasswordRequest;
import com.example.jwtauthapi.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {


    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/change-password")
    public String changePassword(
            @RequestBody ChangePasswordRequest request,
            Authentication authentication) {


        String email = authentication.getName();


        userService.changePassword(
                email,
                request
        );


        return "Password changed successfully";
    }
}