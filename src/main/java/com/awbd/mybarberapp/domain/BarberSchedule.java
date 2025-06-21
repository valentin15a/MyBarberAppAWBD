package com.awbd.mybarberapp.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.util.List;


@Getter
@Setter
@Entity
public class BarberSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "barber_id", nullable = false)
    private Account barber;

    private DayOfWeek day;

    @ElementCollection
    @CollectionTable(name = "barber_schedule_hours", joinColumns = @JoinColumn(name = "schedule_id"))
    @Column(name = "hour")
    private List<String> hours; // Ex: ["09:00", "10:30", "14:00"]
}

