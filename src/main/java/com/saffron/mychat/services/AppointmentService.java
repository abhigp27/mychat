package com.saffron.mychat.services;

import com.saffron.mychat.entity.Appointment;
import com.saffron.mychat.handler.CustomExceptions.BadRequestException;
import com.saffron.mychat.handler.CustomExceptions.NotFoundException;
import com.saffron.mychat.repository.AppointmentRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public Mono<Appointment> bookAppointment(Appointment appointment) {
        // Add any validation logic here
        if (appointment.getStartTime().isAfter(appointment.getEndTime())) {
            return Mono.error(new BadRequestException("Start time must be before end time"));
        }
        return appointmentRepository.save(appointment);
    }

    public Flux<Appointment> getAppointmentsByUser(Long userId) {
        if (userId == null) {
            return Flux.error(new BadRequestException("User ID cannot be null"));
        }
        return appointmentRepository.findByUserId(userId);
    }

    public Flux<Appointment> getAppointmentsByBusiness(Long businessId) {
        if (businessId == null) {
            return Flux.error(new BadRequestException("Business ID cannot be null"));
        }
        return appointmentRepository.findByBusinessId(businessId);
    }

    public Mono<Void> cancelAppointment(Long id) {
        return appointmentRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Appointment not found")))
                .flatMap(existingAppointment -> {
                    existingAppointment.setStatus("cancelled");
                    return appointmentRepository.save(existingAppointment);
                })
                .then();
    }

    public Mono<Appointment> updateAppointment(Long id, Appointment appointment) {
        if (appointment == null || id == null) {
            return Mono.error(new BadRequestException("Invalid input"));
        }
        return appointmentRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Appointment not found")))
                .flatMap(existingAppointment -> {
                    existingAppointment.setStartTime(appointment.getStartTime());
                    existingAppointment.setEndTime(appointment.getEndTime());
                    existingAppointment.setStatus(appointment.getStatus());
                    return appointmentRepository.save(existingAppointment);
                });
    }
}
