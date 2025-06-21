package com.awbd.mybarberapp.services;

import com.awbd.mybarberapp.dtos.BarberScheduleDTO;

import java.time.DayOfWeek;
import java.util.List;

public interface BarberScheduleService {
    void saveOrUpdate(BarberScheduleDTO dto);

    BarberScheduleDTO getScheduleForBarberAndDay(Long barberId, DayOfWeek day);

    List<BarberScheduleDTO> getAllSchedulesForBarber(Long barberId);
}

