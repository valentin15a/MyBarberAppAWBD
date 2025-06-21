package com.awbd.mybarberapp.repositories;

import com.awbd.mybarberapp.domain.BarberSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

@Repository
public interface BarberScheduleRepository extends JpaRepository<BarberSchedule, Long> {

    Optional<BarberSchedule> findByBarberIdAndDay(Long barberId, DayOfWeek day);

    List<BarberSchedule> findByBarberId(Long barberId);
}
