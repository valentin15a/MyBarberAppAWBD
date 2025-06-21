package com.awbd.mybarberapp.repositories;

import com.awbd.mybarberapp.domain.BarberProcedure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BarberProcedureRepository extends JpaRepository<BarberProcedure, Long> {

    List<BarberProcedure> findAllByBarberId(Long barberId);
}
