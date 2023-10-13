package com.saffron.mychat.controller;

import com.saffron.mychat.entity.Appointment;
import com.saffron.mychat.services.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping("/book")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Appointment> bookAppointment(@RequestBody Appointment appointment) {
        if (appointment == null) {
            return Mono.error(new IllegalArgumentException("Appointment cannot be null"));
        }
        // Add additional validations here as necessary
        return appointmentService.bookAppointment(appointment);
    }

    @GetMapping("/user/{userId}")
    public Flux<Appointment> getAppointmentsByUser(@PathVariable Long userId) {
        if (userId == null) {
            return Flux.error(new IllegalArgumentException("User ID cannot be null"));
        }
        return appointmentService.getAppointmentsByUser(userId);
    }

    @GetMapping("/business/{businessId}")
    public Flux<Appointment> getAppointmentsByBusiness(@PathVariable Long businessId) {
        if (businessId == null) {
            return Flux.error(new IllegalArgumentException("Business ID cannot be null"));
        }
        return appointmentService.getAppointmentsByBusiness(businessId);
    }

    @DeleteMapping("/cancel/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> cancelAppointment(@PathVariable Long id) {
        if (id == null) {
            return Mono.error(new IllegalArgumentException("Appointment ID cannot be null"));
        }
        return appointmentService.cancelAppointment(id);
    }

    @PutMapping("/update/{id}")
    public Mono<Appointment> updateAppointment(@PathVariable Long id, @RequestBody Appointment appointment) {
        if (id == null || appointment == null) {
            return Mono.error(new IllegalArgumentException("Appointment ID and Appointment body cannot be null"));
        }
        // Add additional validations here as necessary
        return appointmentService.updateAppointment(id, appointment);
    }
}

