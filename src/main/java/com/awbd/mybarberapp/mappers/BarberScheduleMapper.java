package com.awbd.mybarberapp.mappers;

import com.awbd.mybarberapp.domain.BarberSchedule;
import com.awbd.mybarberapp.dtos.BarberScheduleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BarberScheduleMapper {

    @Mapping(source = "barber.id", target = "barberId")
    BarberScheduleDTO toDto(BarberSchedule entity);

    @Mapping(source = "barberId", target = "barber.id")
    BarberSchedule toEntity(BarberScheduleDTO dto);
}
