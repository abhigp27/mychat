package com.saffron.mychat.controller;

import com.saffron.mychat.entity.CatalogItem;
import com.saffron.mychat.services.CatalogItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/catalog")
public class CatalogItemController {

    private final CatalogItemService catalogItemService;

    @Autowired
    public CatalogItemController(CatalogItemService catalogItemService) {
        this.catalogItemService = catalogItemService;
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<CatalogItem> addItem(@RequestBody CatalogItem item) {
        if (item == null) {
            return Mono.error(new IllegalArgumentException("Catalog item information cannot be null"));
        }
        // Additional validations can be added here if necessary
        return catalogItemService.addItem(item);
    }

    @GetMapping("/business/{businessId}")
    public Flux<CatalogItem> getItemsByBusinessId(@PathVariable Long businessId) {
        if (businessId == null) {
            return Flux.error(new IllegalArgumentException("Business ID cannot be null"));
        }
        return catalogItemService.getItemsByBusinessId(businessId);
    }

    @PutMapping("/update/{itemId}")
    public Mono<CatalogItem> updateItem(@PathVariable Long itemId, @RequestBody CatalogItem item) {
        if (itemId == null || item == null) {
            return Mono.error(new IllegalArgumentException("Item ID and item body cannot be null"));
        }
        // Additional validations can be added here if necessary
        return catalogItemService.updateItem(itemId, item);
    }

    @DeleteMapping("/delete/{itemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteItem(@PathVariable Long itemId) {
        if (itemId == null) {
            return Mono.error(new IllegalArgumentException("Item ID cannot be null"));
        }
        return catalogItemService.deleteItem(itemId);
    }
}


