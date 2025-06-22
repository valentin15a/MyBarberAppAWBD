package com.awbd.mybarberapp.mappers;

import com.awbd.mybarberapp.domain.Account;
import com.awbd.mybarberapp.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BarberNameResolver {

    private final AccountRepository accountRepository;

    @Autowired
    public BarberNameResolver(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public String resolve(Long barberId) {
        return accountRepository.findById(barberId)
                .map(Account::getUsername) // sau .getFullName() dacă ai
                .orElse("Frizer necunoscut");
    }
}
