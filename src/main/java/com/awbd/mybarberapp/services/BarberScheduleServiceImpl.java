package com.awbd.mybarberapp.services;

import com.awbd.mybarberapp.domain.Account;
import com.awbd.mybarberapp.domain.BarberSchedule;
import com.awbd.mybarberapp.dtos.BarberScheduleDTO;
import com.awbd.mybarberapp.mappers.BarberScheduleMapper;
import com.awbd.mybarberapp.repositories.AccountRepository;
import com.awbd.mybarberapp.repositories.BarberScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class BarberScheduleServiceImpl implements BarberScheduleService {

    private final BarberScheduleRepository repository;
    private final BarberScheduleMapper mapper;
    private final AccountRepository accountRepository;

    @Override
    public void saveOrUpdate(BarberScheduleDTO dto) {
        Account barber = accountRepository.findById(dto.getBarberId())
                .orElseThrow(() -> new NoSuchElementException("Frizerul nu a fost găsit"));

        BarberSchedule schedule = repository.findByBarberIdAndDay(dto.getBarberId(), DayOfWeek.valueOf(dto.getDay()))
                .orElse(new BarberSchedule());

        schedule.setBarber(barber);
        schedule.setDay(DayOfWeek.valueOf(dto.getDay()));
        schedule.setHours(dto.getHours());

        repository.save(schedule);
    }


    @Override
    public BarberScheduleDTO getScheduleForBarberAndDay(Long barberId, DayOfWeek day) {
        return repository.findByBarberIdAndDay(barberId, day)
                .map(mapper::toDto)
                .orElseGet(() -> {
                    BarberScheduleDTO dto = new BarberScheduleDTO();
                    dto.setBarberId(barberId);
                    dto.setDay(String.valueOf(day));
                    dto.setHours(Collections.emptyList());
                    return dto;
                });
    }

    @Override
    public List<BarberScheduleDTO> getAllSchedulesForBarber(Long barberId) {
        return repository.findByBarberId(barberId).stream()
                .map(mapper::toDto)
                .toList();
    }
}


