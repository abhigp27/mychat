package com.saffron.mychat.repository;

import com.saffron.mychat.entity.Appointment;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface AppointmentRepository extends ReactiveCrudRepository<Appointment, Long> {
    Flux<Appointment> findByUserId(Long userId);
    Flux<Appointment> findByBusinessId(Long businessId);
}

