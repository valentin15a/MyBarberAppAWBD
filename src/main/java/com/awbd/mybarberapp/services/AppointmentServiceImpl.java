package com.awbd.mybarberapp.services;


import com.awbd.mybarberapp.domain.Appointment;
import com.awbd.mybarberapp.domain.AppointmentStatus;
import com.awbd.mybarberapp.dtos.AppointmentDTO;
import com.awbd.mybarberapp.mappers.AppointmentMapper;
import com.awbd.mybarberapp.mappers.BarberNameResolver;
import com.awbd.mybarberapp.mappers.ClientNameResolver;
import com.awbd.mybarberapp.repositories.AppointmentRepository;
import com.awbd.mybarberapp.repositories.BarberScheduleRepository;
import com.awbd.mybarberapp.services.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final BarberScheduleRepository barberScheduleRepository;
    private final AppointmentMapper appointmentMapper;
    private final ClientNameResolver clientNameResolver;
    private final BarberNameResolver barberNameResolver;


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

    @Override
    public List<String> getAvailableHoursForBarber(Long barberId, LocalDate date) {
        // Găsește ziua săptămânii
        DayOfWeek day = date.getDayOfWeek();

        // Obține programul frizerului pentru ziua respectivă
        List<String> scheduledHours = barberScheduleRepository.findByBarberIdAndDay(barberId, day)
                .map(schedule -> schedule.getHours())
                .orElse(List.of());

        // Obține orele deja rezervate
        List<String> bookedHours = appointmentRepository.findByBarberIdAndDate(barberId, date)
                .stream()
                .map(Appointment::getTime)
                .toList();

        // Filtrare: ore din program care nu sunt deja rezervate
        return scheduledHours.stream()
                .filter(hour -> !bookedHours.contains(hour))
                .toList();
    }

    @Override
    public List<AppointmentDTO> getAppointmentsByBarberAndStatus(Long barberId, String status) {
        List<Appointment> appointments = appointmentRepository
                .findByBarberIdAndStatus(barberId, AppointmentStatus.valueOf(status));

        return appointments.stream()
                .map(appt -> appointmentMapper.toDto(appt, clientNameResolver,barberNameResolver)) // ← cu context!
                .toList();
    }

    @Override
    public List<AppointmentDTO> getPastAppointmentsForBarber(Long barberId) {
        List<Appointment> allAppointments = appointmentRepository.findByBarberId(barberId);

        return allAppointments.stream()
                .filter(appt -> appt.getStatus() != AppointmentStatus.CREATED) // excludem doar cele noi
                .map(appt -> appointmentMapper.toDto(appt, clientNameResolver,barberNameResolver)) // ← dacă folosești @Context
                .toList();
    }
    @Override
    public List<AppointmentDTO> getByClientIdDto(Long clientId) {
        return appointmentRepository.findByClientId(clientId).stream()
                .map(appt -> appointmentMapper.toDto(appt, clientNameResolver, barberNameResolver))
                .toList();
    }

    @Override
    public Page<AppointmentDTO> getByClientIdPaginated(Long clientId, int page, int size, String sortField, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Appointment> appointmentsPage = appointmentRepository.findByClientId(clientId, pageable);

        return appointmentsPage.map(appt -> appointmentMapper.toDto(appt, clientNameResolver, barberNameResolver));
    }




}
