package com.quizapp.user.controller;

import com.quizapp.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> getCurrentUser(Authentication userDetails){
        return ResponseEntity.ok(userService.getCurrentUser(userDetails));
    }

}
