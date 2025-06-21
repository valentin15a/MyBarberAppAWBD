package com.awbd.mybarberapp.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "appointment")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    private String time; // ex: "10:30"

    @ManyToMany
    @JoinTable(
            name = "appointment_procedures",
            joinColumns = @JoinColumn(name = "appointment_id"),
            inverseJoinColumns = @JoinColumn(name = "barber_procedure_id")
    )
    private List<BarberProcedure> services;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status = AppointmentStatus.CREATED;

    private double price;

    @Column(name = "client_id")
    private Long clientId;

    @Column(name = "barber_id")
    private Long barberId;
}
