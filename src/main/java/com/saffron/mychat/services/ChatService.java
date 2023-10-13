package com.saffron.mychat.services;

import com.saffron.mychat.entity.Chat;
import com.saffron.mychat.handler.CustomExceptions.BadRequestException;
import com.saffron.mychat.handler.CustomExceptions.NotFoundException;
import com.saffron.mychat.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

    /**
     * Creates a new Chat.
     *
     * @param chat the chat object to be saved.
     * @return the saved chat object.
     */
    public Mono<Chat> createChat(Chat chat) {
        if (chat == null) {
            return Mono.error(new BadRequestException("Chat object cannot be null"));
        }
        return chatRepository.save(chat);
    }

    /**
     * Gets a Chat by its ID.
     *
     * @param id the ID of the chat to retrieve.
     * @return the retrieved chat object.
     */
    public Mono<Chat> getChatById(Long id) {
        if (id == null) {
            return Mono.error(new BadRequestException("ID cannot be null"));
        }
        return chatRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Chat not found")));
    }

    /**
     * Deletes a Chat by its ID.
     *
     * @param id the ID of the chat to be deleted.
     * @return Mono<Void> upon successful deletion.
     */
    public Mono<Void> deleteChat(Long id) {
        if (id == null) {
            return Mono.error(new BadRequestException("ID cannot be null"));
        }
        return chatRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Chat not found")))
                .flatMap(existingChat -> chatRepository.deleteById(id));
    }

    public Mono<Chat> updateChat(Long id, Chat chat) {
        // Validate input
        if (id == null || chat == null) {
            return Mono.error(new BadRequestException("Invalid input"));
        }
        return chatRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Chat not found")))
                .flatMap(existingChat -> {
                    // update fields here, for example:
//                    existingChat.setSomeField(chat.getSomeField());
                    return chatRepository.save(existingChat);
                });
    }
}


