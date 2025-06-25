package com.awbd.mybarberapp;

import com.awbd.mybarberapp.domain.Account;
import com.awbd.mybarberapp.repositories.AccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("h2")
class AccountRepositoryTest {

    @Autowired
    AccountRepository accountRepository;

    @Test
    @DisplayName("Save and find Account by email")
    void testSaveAndFindByEmail() {
        Account account = Account.builder()
                .email("barber@example.com")
                .username("barber01")
                .password("secure123")
                .build();

        accountRepository.save(account);

        Optional<Account> found = accountRepository.findByEmail("barber@example.com");

        assertThat(found).isPresent();
        assertThat(found.get().getUsername()).isEqualTo("barber01");
    }
}
