package com.awbd.mybarberapp.mappers;

import com.awbd.mybarberapp.domain.Account;
import com.awbd.mybarberapp.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClientNameResolver {

    private final AccountRepository accountRepository;

    @Autowired
    public ClientNameResolver(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public String resolve(Long clientId) {
        return accountRepository.findById(clientId)
                .map(Account::getUsername)
                .orElse("Client necunoscut");
    }
}
