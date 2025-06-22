package com.awbd.mybarberapp.services;

import com.awbd.mybarberapp.domain.Account;
import com.awbd.mybarberapp.domain.BarberProcedure;
import com.awbd.mybarberapp.domain.HairProcedure;
import com.awbd.mybarberapp.dtos.BarberProcedureDTO;
import com.awbd.mybarberapp.mappers.BarberProcedureMapper;
import com.awbd.mybarberapp.repositories.AccountRepository;
import com.awbd.mybarberapp.repositories.BarberProcedureRepository;
import com.awbd.mybarberapp.repositories.HairProcedureRepository;
import com.awbd.mybarberapp.repositories.security.UserRepository;
import com.awbd.mybarberapp.services.security.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class BarberProcedureServiceImpl implements BarberProcedureService {

    private final BarberProcedureRepository repository;
    private final BarberProcedureMapper mapper;
    private final HairProcedureRepository hairProcedureRepository;
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    @Override
    public List<BarberProcedureDTO> getAllForBarber(Long barberId) {
        return repository.findAllByBarberId(barberId)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public BarberProcedureDTO add(BarberProcedureDTO dto) {
        BarberProcedure entity = mapper.toEntity(dto);
        return mapper.toDto(repository.save(entity));
    }

    @Override
    public BarberProcedure findEntityById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Procedura nu a fost găsită"));
    }


    @Override
    public BarberProcedureDTO findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new NoSuchElementException("Procedura nu a fost găsită"));
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    @Override
    public void saveOrUpdate(BarberProcedureDTO dto) {
        String name = dto.getProcedureName().trim();
        System.out.println("💾 HairProcedure Name= " + name);
        HairProcedure procedure = hairProcedureRepository.findByName(name).orElse(null);
        if (procedure == null) {
            procedure = new HairProcedure();
            procedure.setName(name);
            procedure = hairProcedureRepository.saveAndFlush(procedure);
            System.out.println("💾 HairProcedure created: ID = " + procedure.getId());
        } else {
            System.out.println("✅ HairProcedure exists: ID = " + procedure.getId());
        }


        Account barber = accountRepository.findById(dto.getBarberId())
                .orElseThrow(() -> new UsernameNotFoundException("Barber not found"));

        BarberProcedure bp;
        if (dto.getId() != null) {
            bp = repository.findById(dto.getId())
                    .orElseThrow(() -> new NoSuchElementException("Procedura nu există"));
            System.out.println("🔄 UPDATE: BP ID = " + bp.getId());
        } else {
            bp = new BarberProcedure();
            bp.setBarber(barber);
            System.out.println("➕ NEW BP for barber ID = " + barber.getId());
        }


        bp.setProcedure(procedure);
        bp.setPrice(dto.getPrice());

        repository.save(bp);
        System.out.println("✅ BP saved. ProcedureID = " + procedure.getId() + ", Price = " + bp.getPrice());
    }
}


