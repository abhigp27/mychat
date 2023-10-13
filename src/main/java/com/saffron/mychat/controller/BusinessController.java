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

    @Autowired
    private BusinessService businessService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Business> registerBusiness(@RequestBody Business business) {
        return businessService.registerBusiness(business);
    }

    @PutMapping("/update/{id}")
    public Mono<Business> updateBusiness(@PathVariable Long id, @RequestBody Business business) {
        return businessService.updateBusiness(id, business);
    }

    @GetMapping("/{id}")
    public Mono<Business> getBusinessById(@PathVariable Long id) {
        return businessService.getBusinessById(id);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteBusiness(@PathVariable Long id) {
        return businessService.deleteBusiness(id);
    }
}

