package com.awbd.mybarberapp.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "barber_procedure")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BarberProcedure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double price;

    @ManyToOne
    @JoinColumn(name = "barber_id")
    private Account barber;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "procedure_id", nullable = false)
    private HairProcedure procedure;

}

