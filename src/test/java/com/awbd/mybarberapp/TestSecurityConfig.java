package com.awbd.mybarberapp;

import com.awbd.mybarberapp.repositories.security.UserRepository;
import com.awbd.mybarberapp.services.security.JpaUserDetailsService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestSecurityConfig {

    @Bean
    public JpaUserDetailsService jpaUserDetailsService(UserRepository userRepository) {
        return new JpaUserDetailsService(userRepository);
    }
}
