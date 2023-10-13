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

    @Autowired
    private ChatService chatService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Chat> createChat(@RequestBody Chat chat) {
        return chatService.createChat(chat);
    }

    @GetMapping("/{id}")
    public Mono<Chat> getChatById(@PathVariable Long id) {
        return chatService.getChatById(id);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteChat(@PathVariable Long id) {
        return chatService.deleteChat(id);
    }
}

