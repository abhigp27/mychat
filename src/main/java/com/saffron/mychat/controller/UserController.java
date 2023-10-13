package com.saffron.mychat.controller;

import com.saffron.mychat.entity.User;
import com.saffron.mychat.services.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import com.saffron.mychat.handler.CustomExceptions.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ResponseEntity<String>> registerUser(@RequestBody User user) {
        return userService.registerUser(user)
                .then(Mono.just(new ResponseEntity<>("User registered successfully", HttpStatus.CREATED)))
                .onErrorResume(e -> {
                    if(e.getMessage().equals("User already exists")) {
                        return Mono.just(new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT));
                    }
                    return Mono.just(new ResponseEntity<>("Registration failed", HttpStatus.BAD_REQUEST));
                });
    }

    @GetMapping("/email/{email}")
    public Mono<User> getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email)
                .switchIfEmpty(Mono.error(new UserNotFoundException("User not found")));
    }

    @PostMapping("/login")
    public Mono<Map<String, String>> login(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        String password = payload.get("password");
        return userService.getUserByEmail(email)
                .flatMap(user -> {
                    if (bCryptPasswordEncoder.matches(password, user.getPasswordHash())) {
                        String jwtToken = Jwts.builder()
                                .setSubject(email)
                                .setExpiration(new Date(System.currentTimeMillis() + 864_000_000)) // 10 days
                                .signWith(SignatureAlgorithm.HS512, "YourSecretKey")
                                .compact();

                        Map<String, String> response = new HashMap<>();
                        response.put("token", jwtToken);
                        return Mono.just(response);
                    } else {
                        return Mono.error(new UnauthorizedException("Invalid credentials"));
                    }
                });
    }

    @GetMapping("/{user_id}/profile")
    public Mono<User> getProfile(@PathVariable Long user_id) {
        return userService.getUserById(user_id)
                .switchIfEmpty(Mono.error(new UserNotFoundException("User not found")));
    }

    @PutMapping("/{user_id}/profile")
    public Mono<User> updateProfile(@PathVariable Long user_id, @RequestBody Map<String, String> payload) {
        return userService.updateUserProfile(user_id, payload)
                .switchIfEmpty(Mono.error(new UserNotFoundException("User not found")));
    }
}

