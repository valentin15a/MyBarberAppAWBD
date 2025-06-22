package com.awbd.mybarberapp.services;

import com.awbd.mybarberapp.domain.Account;
import com.awbd.mybarberapp.domain.Authority;
import com.awbd.mybarberapp.dtos.AccountDTO;
import com.awbd.mybarberapp.mappers.AccountMapper;
import com.awbd.mybarberapp.repositories.AccountRepository;
import com.awbd.mybarberapp.repositories.security.AuthorityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AuthorityRepository authorityRepository;
    private final AccountMapper accountMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void registerAccount(AccountDTO dto) {
        if (accountRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email deja folosit");
        }

        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new IllegalArgumentException("Parolele nu se potrivesc");
        }

        Account account = accountMapper.toAccount(dto);
        account.setPassword(passwordEncoder.encode(dto.getPassword()));
        account.setEnabled(true);

        String requestedRole = dto.getRole().toUpperCase().trim();

        Authority role = authorityRepository.findByRole(requestedRole)
                .orElseGet(() -> authorityRepository.save(
                        Authority.builder().role(requestedRole).build()
                ));

        account.setAuthorities(Set.of(role));


        accountRepository.save(account);
    }
}

