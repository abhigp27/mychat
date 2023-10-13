package com.saffron.mychat.services;

import com.saffron.mychat.entity.Media;
import com.saffron.mychat.repository.MediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MediaService {

    private final MediaRepository mediaRepository;

    @Autowired
    public MediaService(MediaRepository mediaRepository) {
        this.mediaRepository = mediaRepository;
    }

    /**
     * Uploads media and saves it in the database.
     *
     * @param media the media to be uploaded
     * @return Mono emitting the saved media
     */
    public Mono<Media> uploadMedia(Media media) {
        if (media == null) {
            return Mono.error(new IllegalArgumentException("Media object cannot be null"));
        }
        // Add any other validation or transformation steps here
        return mediaRepository.save(media);
    }

    /**
     * Lists media associated with a related entity.
     *
     * @param relatedEntityId   the ID of the related entity
     * @param relatedEntityType the type of the related entity
     * @return Flux emitting the list of media
     */
    public Flux<Media> listMedia(Long relatedEntityId, String relatedEntityType) {
        if (relatedEntityId == null || relatedEntityType == null) {
            return Flux.error(new IllegalArgumentException("Entity ID and type cannot be null"));
        }
        return mediaRepository.findByRelatedEntityIdAndRelatedEntityType(relatedEntityId, relatedEntityType);
    }

    /**
     * Deletes media by its ID.
     *
     * @param id the ID of the media to be deleted
     * @return Mono emitting completion signal
     */
    public Mono<Void> deleteMedia(Long id) {
        if (id == null) {
            return Mono.error(new IllegalArgumentException("Media ID cannot be null"));
        }
        return mediaRepository.existsById(id)
                .flatMap(exists -> {
                    if (exists) {
                        return mediaRepository.deleteById(id);
                    } else {
                        return Mono.error(new IllegalArgumentException("Media with the given ID does not exist"));
                    }
                });
    }
}


