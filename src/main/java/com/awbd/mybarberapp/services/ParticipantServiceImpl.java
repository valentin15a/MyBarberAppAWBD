package com.awbd.mybarberapp.services;

import com.awbd.mybarberapp.domain.Participant;
import com.awbd.mybarberapp.dtos.ParticipantDTO;
import com.awbd.mybarberapp.mappers.ParticipantMapper;
import com.awbd.mybarberapp.repositories.ParticipantRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ParticipantServiceImpl implements ParticipantService{

    private final ParticipantRepository participantRepository;
    private final ParticipantMapper participantMapper;

    ParticipantServiceImpl(ParticipantRepository participantRepository, ParticipantMapper participantMapper){
        this.participantRepository = participantRepository;
        this.participantMapper = participantMapper;
    }

    @Override
    public List<ParticipantDTO> findAll(){
        List<Participant> participants = new LinkedList<>();
        participantRepository.findAll().iterator().forEachRemaining(participants::add);

        return participants.stream()
                .map(participantMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ParticipantDTO findById(Long l) {
        Optional<Participant> participantOptional = participantRepository.findById(l);
        if (!participantOptional.isPresent()) {
            throw new RuntimeException("Participant not found!");
        }

        return participantMapper.toDto(participantOptional.get());
    }

    @Override
    public ParticipantDTO save(ParticipantDTO participantDto) {
        Participant savedParticipant = participantRepository.save(participantMapper.toParticipant(participantDto));
        return participantMapper.toDto(savedParticipant);
    }

    @Override
    public void deleteById(Long id) {
        participantRepository.deleteById(id);
    }


}
