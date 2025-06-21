package com.awbd.mybarberapp.mappers;

import com.awbd.mybarberapp.domain.HairProcedure;
import com.awbd.mybarberapp.dtos.HairProcedureDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HairProcedureMapper {
    HairProcedureDTO toDto(HairProcedure entity);
    HairProcedure toEntity(HairProcedureDTO dto);
}

