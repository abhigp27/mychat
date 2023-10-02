package com.saffron.mychat.controller;

import com.saffron.mychat.dto.RegistrationRequest;
import com.saffron.mychat.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public Mono<ResponseEntity<String>> registerUser(@RequestBody RegistrationRequest request) {
        return userService.registerUser(request.getName(), request.getEmail(), request.getPassword())
                .then(Mono.just(new ResponseEntity<>("User registered successfully", HttpStatus.CREATED)))
                .onErrorResume(e -> {
                    if(e.getMessage().equals("User already exists")) {
                        return Mono.just(new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT));
                    }
                    return Mono.just(new ResponseEntity<>("Registration failed", HttpStatus.BAD_REQUEST));
                });
    }
}
