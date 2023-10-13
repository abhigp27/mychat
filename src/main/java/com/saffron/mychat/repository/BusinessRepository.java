package com.saffron.mychat.repository;

import com.saffron.mychat.entity.Business;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface BusinessRepository extends ReactiveCrudRepository<Business, Long> {
}

