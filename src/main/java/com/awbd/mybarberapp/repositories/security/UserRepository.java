package com.awbd.mybarberapp.repositories.security;

import com.awbd.mybarberapp.services.security.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u JOIN u.authorities a WHERE a.role = :role")
    List<User> findAllByAuthorityRole(@Param("role") String role);

}
