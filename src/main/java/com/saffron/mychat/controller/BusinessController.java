package com.saffron.mychat.controller;

import com.saffron.mychat.entity.Business;
import com.saffron.mychat.services.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/business")
public class BusinessController {

    private final BusinessService businessService;

    @Autowired
    public BusinessController(BusinessService businessService) {
        this.businessService = businessService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Business> registerBusiness(@RequestBody Business business) {
        if (business == null) {
            return Mono.error(new IllegalArgumentException("Business information cannot be null"));
        }
        // Add additional validations here as necessary
        return businessService.registerBusiness(business);
    }

    @PutMapping("/update/{id}")
    public Mono<Business> updateBusiness(@PathVariable Long id, @RequestBody Business business) {
        if (id == null || business == null) {
            return Mono.error(new IllegalArgumentException("Business ID and body cannot be null"));
        }
        // Add additional validations here as necessary
        return businessService.updateBusiness(id, business);
    }

    @GetMapping("/{id}")
    public Mono<Business> getBusinessById(@PathVariable Long id) {
        if (id == null) {
            return Mono.error(new IllegalArgumentException("Business ID cannot be null"));
        }
        return businessService.getBusinessById(id);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteBusiness(@PathVariable Long id) {
        if (id == null) {
            return Mono.error(new IllegalArgumentException("Business ID cannot be null"));
        }
        return businessService.deleteBusiness(id);
    }
}


