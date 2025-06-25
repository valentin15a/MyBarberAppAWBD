package com.awbd.mybarberapp;

import com.awbd.mybarberapp.domain.HairProcedure;
import com.awbd.mybarberapp.repositories.HairProcedureRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("h2")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class HairProcedureRepositoryTest {

    @Autowired
    HairProcedureRepository repository;

    @Test
    @DisplayName("save & findByName")
    void testFindByName() {
        HairProcedure p = new HairProcedure();
        p.setName("Color");
        repository.save(p);

        Optional<HairProcedure> opt = repository.findByName("Color");
        assertThat(opt).isPresent();
        assertThat(opt.get().getName()).isEqualTo("Color");
    }
}
