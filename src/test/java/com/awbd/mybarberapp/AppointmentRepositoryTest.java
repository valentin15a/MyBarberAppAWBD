package com.awbd.mybarberapp;

import com.awbd.mybarberapp.domain.Appointment;
import com.awbd.mybarberapp.domain.AppointmentStatus;
import com.awbd.mybarberapp.repositories.AppointmentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("h2")
class AppointmentRepositoryTest {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Test
    @DisplayName("Save and retrieve Appointment by barberId and date")
    void shouldSaveAndFindAppointment() {
        // Arrange
        Appointment appointment = new Appointment();
        appointment.setBarberId(1L);
        appointment.setClientId(2L);
        appointment.setDate(LocalDate.of(2025, 6, 24));
        appointment.setTime("10:00");
        appointment.setStatus(AppointmentStatus.CREATED);

        appointmentRepository.save(appointment);

        // Act
        List<Appointment> found = appointmentRepository.findByBarberIdAndDate(1L, LocalDate.of(2025, 6, 24));

        // Assert
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getTime()).isEqualTo("10:00");
        assertThat(found.get(0).getStatus()).isEqualTo(AppointmentStatus.CREATED);
    }
}
