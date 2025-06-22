package com.awbd.mybarberapp.repositories;

import com.awbd.mybarberapp.domain.Appointment;
import com.awbd.mybarberapp.domain.AppointmentStatus;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByBarberIdAndDate(Long barberId, LocalDate date);
    List<Appointment> findByClientId(Long clientId);
    List<Appointment> findByStatus(AppointmentStatus status);
    List<Appointment> findByBarberIdAndStatus(Long barberId, AppointmentStatus status);
    List<Appointment> findByBarberId(Long barberId);
    Page<Appointment> findByClientId(Long clientId, Pageable pageable);
}
