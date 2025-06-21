package com.awbd.mybarberapp.repositories.security;

import com.awbd.mybarberapp.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
    Optional<Authority> findByRole(String role);
}
