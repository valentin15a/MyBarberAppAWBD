package com.awbd.mybarberapp.services;

import com.awbd.mybarberapp.dtos.ParticipantDTO;

import java.util.List;

public interface ParticipantService {

    List<ParticipantDTO> findAll();
    ParticipantDTO findById(Long l);
    ParticipantDTO save(ParticipantDTO participant);
    void deleteById(Long id);

}
