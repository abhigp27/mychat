package com.saffron.mychat.repository;

import com.saffron.mychat.entity.CatalogItem;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface CatalogItemRepository extends ReactiveCrudRepository<CatalogItem, Long> {
    Flux<CatalogItem> findByBusinessId(Long businessId);
}
