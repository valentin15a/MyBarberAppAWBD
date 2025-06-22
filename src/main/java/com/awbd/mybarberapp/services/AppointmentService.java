package com.awbd.mybarberapp.services;

import com.awbd.mybarberapp.domain.Appointment;
import com.awbd.mybarberapp.domain.AppointmentStatus;
import com.awbd.mybarberapp.dtos.AppointmentDTO;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentService {

    Appointment save(Appointment appointment);
    Appointment getById(Long id);
    List<Appointment> getByClientId(Long clientId);
    List<Appointment> getByBarberIdAndDate(Long barberId, LocalDate date);
    List<Appointment> getByStatus(AppointmentStatus status);
    void delete(Long id);
    List<String> getAvailableHoursForBarber(Long barberId, LocalDate date);
    List<AppointmentDTO> getAppointmentsByBarberAndStatus(Long barberId, String status);
    List<AppointmentDTO> getPastAppointmentsForBarber(Long barberId);
    List<AppointmentDTO> getByClientIdDto(Long clientId);
    Page<AppointmentDTO> getByClientIdPaginated(Long clientId, int page, int size, String sortField, String sortDir);
    Page<AppointmentDTO> getPastAppointmentsForBarberPaginated(Long barberId, int page, int size, String sortField, String sortDir);



}
