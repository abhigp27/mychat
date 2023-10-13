package com.saffron.mychat.controller;

import com.saffron.mychat.entity.Media;
import com.saffron.mychat.services.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/media")
public class MediaController {

    private final MediaService mediaService;

    @Autowired
    public MediaController(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    @PostMapping("/upload")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Media> uploadMedia(@RequestBody Media media) {
        if (media == null) {
            return Mono.error(new IllegalArgumentException("Media information cannot be null"));
        }
        // Additional validations can be added here if necessary
        return mediaService.uploadMedia(media);
    }

    @GetMapping("/list/{relatedEntityId}/{relatedEntityType}")
    public Flux<Media> listMedia(@PathVariable Long relatedEntityId, @PathVariable String relatedEntityType) {
        if (relatedEntityId == null || relatedEntityType == null) {
            return Flux.error(new IllegalArgumentException("Related entity ID and type cannot be null"));
        }
        // Additional validations can be added here if necessary
        return mediaService.listMedia(relatedEntityId, relatedEntityType);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteMedia(@PathVariable Long id) {
        if (id == null) {
            return Mono.error(new IllegalArgumentException("Media ID cannot be null"));
        }
        // Additional validations can be added here if necessary
        return mediaService.deleteMedia(id);
    }
}


