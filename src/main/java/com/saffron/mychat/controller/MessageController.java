package com.saffron.mychat.controller;

import com.saffron.mychat.entity.Message;
import com.saffron.mychat.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/send")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Message> sendMessage(@RequestBody Message message) {
        if (message == null) {
            return Mono.error(new IllegalArgumentException("Message information cannot be null"));
        }
        // Additional validations can be added here, such as checking the chat ID, user ID, or message content
        return messageService.sendMessage(message);
    }

    @GetMapping("/chat/{chatId}")
    public Flux<Message> getMessagesByChatId(@PathVariable Long chatId) {
        if (chatId == null) {
            return Flux.error(new IllegalArgumentException("Chat ID cannot be null"));
        }
        // Additional validations can be added here if necessary
        return messageService.getMessagesByChatId(chatId);
    }
}


