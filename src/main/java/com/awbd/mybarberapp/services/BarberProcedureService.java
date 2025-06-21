package com.awbd.mybarberapp.services;

import com.awbd.mybarberapp.domain.BarberProcedure;
import com.awbd.mybarberapp.dtos.BarberProcedureDTO;

import java.util.List;

public interface BarberProcedureService {
    List<BarberProcedureDTO> getAllForBarber(Long barberId);
    BarberProcedureDTO add(BarberProcedureDTO dto);
    BarberProcedureDTO findById(Long id);
    void deleteById(Long id);
    void saveOrUpdate(BarberProcedureDTO dto);
    BarberProcedure findEntityById(Long id);



}
