package com.awbd.mybarberapp.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Table(name = "appointment")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    private String time;

    @ManyToMany
    @JoinTable(
            name = "appointment_procedures",
            joinColumns = @JoinColumn(name = "appointment_id"),
            inverseJoinColumns = @JoinColumn(name = "procedure_id")
    )
    private List<HairProcedure> procedures;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status = AppointmentStatus.CREATED;

    private double price;

    private Long clientId;

    private Long barberId;
}

