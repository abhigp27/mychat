package com.saffron.mychat.services;

import com.saffron.mychat.entity.User;
import com.saffron.mychat.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Mono<Object> registerUser(String name, String email, String password) {
        return userRepository.findByEmail(email)
                .flatMap(existingUser -> Mono.error(new RuntimeException("User already exists")))
                .switchIfEmpty(
                        Mono.just(new User())
                                .map(newUser -> {
                                    newUser.setName(name);
                                    newUser.setEmail(email);
                                    newUser.setPasswordHash(passwordEncoder.encode(password));
                                    return newUser;
                                })
                                .flatMap(userRepository::save)
                );
    }
}
