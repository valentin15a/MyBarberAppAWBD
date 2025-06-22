package com.awbd.mybarberapp.mappers;

import com.awbd.mybarberapp.domain.Appointment;
import com.awbd.mybarberapp.domain.HairProcedure;
import com.awbd.mybarberapp.dtos.AppointmentDTO;
import com.awbd.mybarberapp.repositories.security.UserRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring", uses = HairProcedureResolver.class)
public interface AppointmentMapper {

    @Mapping(source = "hairProcedureIds", target = "procedures")
    @Mapping(target = "clientId", expression = "java(clientId)")
    @Mapping(target = "status", constant = "CREATED")
    Appointment toEntity(AppointmentDTO dto, @Context Long clientId);


    @Mapping(target = "barberName", expression = "java(barberNameResolver.resolve(appointment.getBarberId()))")
    @Mapping(source = "procedures", target = "hairProcedureIds")
    @Mapping(target = "procedureNames", expression = "java(appointment.getProcedures().stream().map(p -> p.getName()).toList())")
    @Mapping(source = "price", target = "totalPrice")
    @Mapping(target = "status", expression = "java(appointment.getStatus().toString())")
    @Mapping(target = "clientName", expression = "java(clientNameResolver.resolve(appointment.getClientId()))")
    AppointmentDTO toDto(Appointment appointment, @Context ClientNameResolver clientNameResolver,@Context BarberNameResolver barberNameResolver);
}



