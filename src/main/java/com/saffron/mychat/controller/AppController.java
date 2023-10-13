package com.saffron.mychat.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class AppController {
    @GetMapping("/")
    public Mono<String> health() {
        return Mono.just("Hello World !!");
    }
}
