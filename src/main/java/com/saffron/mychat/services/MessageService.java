package com.saffron.mychat.services;

import com.saffron.mychat.entity.Message;
import com.saffron.mychat.handler.CustomExceptions.BadRequestException;
import com.saffron.mychat.handler.CustomExceptions.NotFoundException;
import com.saffron.mychat.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    /**
     * Sends a new message.
     *
     * @param message the message object to be sent.
     * @return the sent message object.
     */
    public Mono<Message> sendMessage(Message message) {
        if (message == null || message.getChatId() == null || message.getTextContent() == null) {
            return Mono.error(new BadRequestException("Invalid message"));
        }
        return messageRepository.save(message);
    }

    /**
     * Retrieves all messages associated with a chat ID.
     *
     * @param chatId the ID of the chat whose messages to retrieve.
     * @return a Flux of messages associated with the chat ID.
     */
    public Flux<Message> getMessagesByChatId(Long chatId) {
        if (chatId == null) {
            return Flux.error(new BadRequestException("Chat ID cannot be null"));
        }
        return messageRepository.findByChatId(chatId);
    }

    /**
     * Deletes a message by its ID.
     *
     * @param messageId the ID of the message to be deleted.
     * @return Mono<Void> upon successful deletion.
     */
    public Mono<Void> deleteMessage(Long messageId) {
        if (messageId == null) {
            return Mono.error(new BadRequestException("Message ID cannot be null"));
        }
        return messageRepository.findById(messageId)
                .switchIfEmpty(Mono.error(new NotFoundException("Message not found")))
                .flatMap(existingMessage -> messageRepository.deleteById(messageId));
    }
}


