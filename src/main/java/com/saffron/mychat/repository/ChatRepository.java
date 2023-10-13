package com.saffron.mychat.repository;

import com.saffron.mychat.entity.Chat;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ChatRepository extends ReactiveCrudRepository<Chat, Long> {
}

