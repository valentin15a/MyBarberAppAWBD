package com.awbd.mybarberapp.mappers;

import com.awbd.mybarberapp.domain.Appointment;
import com.awbd.mybarberapp.domain.AppointmentStatus;
import com.awbd.mybarberapp.domain.BarberProcedure;
import com.awbd.mybarberapp.dtos.AppointmentDTO;
import com.awbd.mybarberapp.services.BarberProcedureService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AppointmentMapper {

    private final BarberProcedureService procedureService;

    public Appointment toEntity(AppointmentDTO dto, Long clientId) {
        List<BarberProcedure> procedures = dto.getBarberProcedureIds().stream()
                .map(procedureService::findEntityById)
                .toList();

        double total = procedures.stream()
                .mapToDouble(BarberProcedure::getPrice)
                .sum();

        return Appointment.builder()
                .date(dto.getDate())
                .time(dto.getTime())
                .barberId(dto.getBarberId())
                .clientId(clientId)
                .services(procedures)
                .price(total)
                .status(AppointmentStatus.CREATED)
                .build();
    }

    public AppointmentDTO toDto(Appointment appointment) {
        return new AppointmentDTO(
                appointment.getDate(),
                appointment.getTime(),
                appointment.getBarberId(),
                appointment.getServices().stream().map(BarberProcedure::getId).toList(),
                appointment.getPrice()
        );
    }
}
