package com.awbd.mybarberapp.services;

import com.awbd.mybarberapp.domain.HairProcedure;
import com.awbd.mybarberapp.repositories.HairProcedureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class HairProcedureServiceImpl implements HairProcedureService {

    private final HairProcedureRepository repository;

    @Override
    public HairProcedure findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Procedura nu a fost găsită"));
    }
}
