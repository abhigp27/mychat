package com.saffron.mychat.repository;

import com.saffron.mychat.entity.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<User, Long> {
    Mono<User> findByEmail(String email);

    Mono<User> findByPhoneNumber(String phoneNumber);
}


