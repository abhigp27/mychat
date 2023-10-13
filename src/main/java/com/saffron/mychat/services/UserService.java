package com.saffron.mychat.services;

import com.saffron.mychat.entity.User;
import com.saffron.mychat.handler.CustomExceptions.BadRequestException;
import com.saffron.mychat.handler.CustomExceptions.DuplicateEntryException;
import com.saffron.mychat.handler.CustomExceptions.NotFoundException;
import com.saffron.mychat.handler.CustomExceptions.UnknownException;
import com.saffron.mychat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /**
     * Register a new user.
     *
     * @param user The user object.
     * @return The saved user object.
     */
    public Mono<User> registerUser(User user) {
        return Mono.justOrEmpty(user) // Use justOrEmpty to handle null user
                .flatMap(newUser -> {
                    // Check if email or phone number already exists
                    Mono<User> existingEmail = userRepository.findByEmail(newUser.getEmail())
                            .defaultIfEmpty(new User());
                    Mono<User> existingPhoneNumber = userRepository.findByPhoneNumber(newUser.getPhoneNumber())
                            .defaultIfEmpty(new User());

                    return Mono.zip(existingEmail, existingPhoneNumber)
                            .flatMap(tuple -> {
                                User emailUser = tuple.getT1();
                                User phoneUser = tuple.getT2();

                                // Check if the default empty User objects are returned
                                if (emailUser.getUserId() != null) {
                                    return Mono.error(new DuplicateEntryException("Email already exists"));
                                }

                                if (phoneUser.getUserId() != null) {
                                    return Mono.error(new DuplicateEntryException("Phone number already exists"));
                                }

                                // All checks passed, encrypt password and save user
                                newUser.setPasswordHash(bCryptPasswordEncoder.encode(newUser.getPasswordHash()));
                                return userRepository.save(newUser);
                            });
                })
                .switchIfEmpty(Mono.error(new UnknownException("Unknown error during registration")));
    }

    /**
     * Fetch user by email.
     *
     * @param email The email of the user.
     * @return The user object.
     */
    public Mono<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .switchIfEmpty(Mono.error(new NotFoundException("User not found")));
    }

    /**
     * Fetch user by ID.
     *
     * @param userId The ID of the user.
     * @return The user object.
     */
    public Mono<User> getUserById(Long userId) {
        return userRepository.findById(userId)
                .switchIfEmpty(Mono.error(new NotFoundException("User not found")));
    }

    /**
     * Update user profile.
     *
     * @param userId  The ID of the user.
     * @param payload The fields to update.
     * @return The updated user object.
     */
    public Mono<User> updateUserProfile(Long userId, Map<String, String> payload) {
        if (payload == null || payload.isEmpty()) {
            return Mono.error(new BadRequestException("Invalid input"));
        }

        return userRepository.findById(userId)
                .switchIfEmpty(Mono.error(new NotFoundException("User not found")))
                .flatMap(existingUser -> {
                    payload.forEach((key, value) -> {
                        switch (key) {
                            case "name":
                                existingUser.setName(value);
                                break;
                            case "phone_number":
                                existingUser.setPhoneNumber(value);
                                break;
                            case "profile_image_url":
                                existingUser.setProfileImage(value);
                                break;
                            default:
                                break;
                        }
                    });
                    return userRepository.save(existingUser);
                });
    }
}
