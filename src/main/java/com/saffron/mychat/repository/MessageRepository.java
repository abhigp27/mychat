package com.saffron.mychat.repository;

import com.saffron.mychat.entity.Message;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface MessageRepository extends ReactiveCrudRepository<Message, Long> {
    Flux<Message> findByChatId(Long chatId);
}
