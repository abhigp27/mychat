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

    @Autowired
    private CatalogItemService catalogItemService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<CatalogItem> addItem(@RequestBody CatalogItem item) {
        return catalogItemService.addItem(item);
    }

    @GetMapping("/business/{businessId}")
    public Flux<CatalogItem> getItemsByBusinessId(@PathVariable Long businessId) {
        return catalogItemService.getItemsByBusinessId(businessId);
    }

    @PutMapping("/update/{itemId}")
    public Mono<CatalogItem> updateItem(@PathVariable Long itemId, @RequestBody CatalogItem item) {
        return catalogItemService.updateItem(itemId, item);
    }

    @DeleteMapping("/delete/{itemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteItem(@PathVariable Long itemId) {
        return catalogItemService.deleteItem(itemId);
    }
}

