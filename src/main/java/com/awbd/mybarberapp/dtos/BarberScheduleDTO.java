package com.awbd.mybarberapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BarberScheduleDTO {
    private Long id;
    private Long barberId;
    private String day;
    private List<String> hours;
}

