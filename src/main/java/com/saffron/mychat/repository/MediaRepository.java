package com.saffron.mychat.repository;

import com.saffron.mychat.entity.Media;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface MediaRepository extends ReactiveCrudRepository<Media, Long> {
    Flux<Media> findByRelatedEntityIdAndRelatedEntityType(Long relatedEntityId, String relatedEntityType);
}

