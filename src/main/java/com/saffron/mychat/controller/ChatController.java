package com.saffron.mychat.controller;

import com.saffron.mychat.entity.Chat;
import com.saffron.mychat.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/chats")
public class ChatController {

    private final ChatService chatService;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Chat> createChat(@RequestBody Chat chat) {
        if (chat == null) {
            return Mono.error(new IllegalArgumentException("Chat information cannot be null"));
        }
        // Additional validations can be added here if necessary
        return chatService.createChat(chat);
    }

    @GetMapping("/{id}")
    public Mono<Chat> getChatById(@PathVariable Long id) {
        if (id == null) {
            return Mono.error(new IllegalArgumentException("Chat ID cannot be null"));
        }
        return chatService.getChatById(id);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteChat(@PathVariable Long id) {
        if (id == null) {
            return Mono.error(new IllegalArgumentException("Chat ID cannot be null"));
        }
        return chatService.deleteChat(id);
    }
}

