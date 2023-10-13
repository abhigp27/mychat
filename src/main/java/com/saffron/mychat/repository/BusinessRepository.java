package com.saffron.mychat.repository;

import com.saffron.mychat.entity.Business;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface BusinessRepository extends ReactiveCrudRepository<Business, Long> {
}

