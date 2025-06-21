package com.awbd.mybarberapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BarberProcedureDTO {
    private Long id;
    private String procedureName;
    private Double price;
    private Long barberId;
    private Long procedureId;
}
