package com.awbd.mybarberapp.dtos;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentDTO {
    private Long id;
    private LocalDate date;
    private String time;

    private List<Long> hairProcedureIds;
    private List<String> procedureNames; // ← Pentru afișare în homepage

    private Double totalPrice;

    private String status;

    private Long clientId;
    private Long barberId;

    private String clientName;
    private String barberName;

}
