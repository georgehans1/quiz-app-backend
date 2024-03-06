package com.quizapp.auth.controller;

import com.quizapp.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public Object Test(Authentication authentication) {
        return authentication.getCredentials();
    }

    @GetMapping("/users")
    public Object user() {
        return userService.getAllUsers();
    }
}
