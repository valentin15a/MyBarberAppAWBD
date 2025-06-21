package com.awbd.mybarberapp.mappers;

import com.awbd.mybarberapp.domain.BarberProcedure;
import com.awbd.mybarberapp.dtos.BarberProcedureDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BarberProcedureMapper {

    @Mapping(source = "barber.id", target = "barberId")
    @Mapping(source = "procedure.id", target = "procedureId") // ← adaugă asta
    @Mapping(source = "procedure.name", target = "procedureName")
    BarberProcedureDTO toDto(BarberProcedure entity);

    @Mapping(source = "barberId", target = "barber.id")
    BarberProcedure toEntity(BarberProcedureDTO dto);
}

