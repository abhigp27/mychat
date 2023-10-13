package com.saffron.mychat.services;

import com.saffron.mychat.entity.CatalogItem;
import com.saffron.mychat.handler.CustomExceptions.BadRequestException;
import com.saffron.mychat.handler.CustomExceptions.NotFoundException;
import com.saffron.mychat.repository.CatalogItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CatalogItemService {

    @Autowired
    private CatalogItemRepository catalogItemRepository;

    public Mono<CatalogItem> addItem(CatalogItem item) {
        // Validate the item object
        if (item.getItemName() == null || item.getItemName().isEmpty()) {
            return Mono.error(new BadRequestException("Item name cannot be empty"));
        }
        return catalogItemRepository.save(item);
    }

    public Flux<CatalogItem> getItemsByBusinessId(Long businessId) {
        return catalogItemRepository.findByBusinessId(businessId);
    }

    public Mono<CatalogItem> updateItem(Long itemId, CatalogItem item) {
        // Validate input
        if (itemId == null || item == null) {
            return Mono.error(new BadRequestException("Invalid input"));
        }
        return catalogItemRepository.findById(itemId)
                .switchIfEmpty(Mono.error(new NotFoundException("Catalog Item not found")))
                .flatMap(existingItem -> {
                    existingItem.setItemName(item.getItemName());
                    existingItem.setDescription(item.getDescription());
                    existingItem.setPrice(item.getPrice());
                    existingItem.setAvailabilityStatus(item.getAvailabilityStatus());
                    existingItem.setImageUrl(item.getImageUrl());
                    return catalogItemRepository.save(existingItem);
                });
    }

    public Mono<Void> deleteItem(Long itemId) {
        return catalogItemRepository.findById(itemId)
                .switchIfEmpty(Mono.error(new NotFoundException("Catalog Item not found")))
                .flatMap(existingItem -> catalogItemRepository.deleteById(itemId));
    }
}

