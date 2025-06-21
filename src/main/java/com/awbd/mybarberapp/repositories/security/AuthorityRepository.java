package com.awbd.mybarberapp.repositories.security;

import com.awbd.mybarberapp.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
}