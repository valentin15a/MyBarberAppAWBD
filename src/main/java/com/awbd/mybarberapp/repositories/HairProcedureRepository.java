package com.awbd.mybarberapp.repositories;

import com.awbd.mybarberapp.domain.HairProcedure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HairProcedureRepository extends JpaRepository<HairProcedure, Long> {
    Optional<HairProcedure> findByName(String name);
}


