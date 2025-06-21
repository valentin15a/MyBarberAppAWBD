package com.awbd.mybarberapp.services;


import com.awbd.mybarberapp.domain.Appointment;
import com.awbd.mybarberapp.domain.AppointmentStatus;
import com.awbd.mybarberapp.repositories.AppointmentRepository;
import com.awbd.mybarberapp.services.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;

    @Override
    public Appointment save(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    @Override
    public Appointment getById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found"));
    }

    @Override
    public List<Appointment> getByClientId(Long clientId) {
        return appointmentRepository.findByClientId(clientId);
    }

    @Override
    public List<Appointment> getByBarberIdAndDate(Long barberId, LocalDate date) {
        return appointmentRepository.findByBarberIdAndDate(barberId, date);
    }

    @Override
    public List<Appointment> getByStatus(AppointmentStatus status) {
        return appointmentRepository.findByStatus(status);
    }

    @Override
    public void delete(Long id) {
        appointmentRepository.deleteById(id);
    }
}
