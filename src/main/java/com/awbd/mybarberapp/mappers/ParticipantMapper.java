package com.awbd.mybarberapp.mappers;

import com.awbd.mybarberapp.domain.Participant;
import com.awbd.mybarberapp.dtos.ParticipantDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface ParticipantMapper {
    ParticipantDTO toDto (Participant participant);
    Participant toParticipant (ParticipantDTO participantDTO);
}
