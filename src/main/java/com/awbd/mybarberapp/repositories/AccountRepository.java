package com.awbd.mybarberapp.repositories;

import com.awbd.mybarberapp.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByEmail(String email);
    Optional<Account> findById(Long id);

    @Query("SELECT a FROM Account a JOIN a.authorities auth WHERE auth.role = :roleName")
    List<Account> findAllByAuthorityName(@Param("roleName") String roleName);



}

