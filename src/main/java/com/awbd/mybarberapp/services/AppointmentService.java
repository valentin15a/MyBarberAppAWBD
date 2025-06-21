package com.awbd.mybarberapp.services;

import com.awbd.mybarberapp.domain.Appointment;
import com.awbd.mybarberapp.domain.AppointmentStatus;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentService {

    Appointment save(Appointment appointment);
    Appointment getById(Long id);
    List<Appointment> getByClientId(Long clientId);
    List<Appointment> getByBarberIdAndDate(Long barberId, LocalDate date);
    List<Appointment> getByStatus(AppointmentStatus status);
    void delete(Long id);
}
