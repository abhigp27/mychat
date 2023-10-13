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

    @Autowired
    private MediaService mediaService;

    @PostMapping("/upload")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Media> uploadMedia(@RequestBody Media media) {
        return mediaService.uploadMedia(media);
    }

    @GetMapping("/list/{relatedEntityId}/{relatedEntityType}")
    public Flux<Media> listMedia(@PathVariable Long relatedEntityId, @PathVariable String relatedEntityType) {
        return mediaService.listMedia(relatedEntityId, relatedEntityType);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteMedia(@PathVariable Long id) {
        return mediaService.deleteMedia(id);
    }
}

