package com.awbd.mybarberapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDTO {
    private LocalDate date;
    private String time;
    private Long barberId;
    private List<Long> barberProcedureIds;
    private Double totalPrice; // prețul total calculat în backend
}
