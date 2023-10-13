package com.saffron.mychat.services;

import com.saffron.mychat.entity.Business;
import com.saffron.mychat.handler.CustomExceptions.BadRequestException;
import com.saffron.mychat.handler.CustomExceptions.NotFoundException;
import com.saffron.mychat.repository.BusinessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
public class BusinessService {

    @Autowired
    private BusinessRepository businessRepository;

    public Mono<Business> registerBusiness(Business business) {
        // Add any validation logic here, such as checking if the business name or email already exists
        if (business.getBusinessName() == null || business.getBusinessName().isEmpty()) {
            return Mono.error(new BadRequestException("Business name cannot be empty"));
        }
        return businessRepository.save(business);
    }

    public Mono<Business> updateBusiness(Long id, Business business) {
        // Validate input
        if (id == null || business == null) {
            return Mono.error(new BadRequestException("Invalid input"));
        }
        return businessRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Business not found")))
                .flatMap(existingBusiness -> {
                    existingBusiness.setBusinessName(business.getBusinessName());
                    existingBusiness.setContactEmail(business.getContactEmail());
                    existingBusiness.setContactPhoneNumber(business.getContactPhoneNumber());
                    existingBusiness.setAddress(business.getAddress());
                    return businessRepository.save(existingBusiness);
                });
    }

    public Mono<Business> getBusinessById(Long id) {
        return businessRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Business not found")));
    }

    public Mono<Void> deleteBusiness(Long id) {
        return businessRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Business not found")))
                .flatMap(existingBusiness -> businessRepository.deleteById(id));
    }
}


