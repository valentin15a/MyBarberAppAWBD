package com.awbd.mybarberapp.bootstrap;

import com.awbd.mybarberapp.domain.Authority;
import com.awbd.mybarberapp.services.security.User;
import com.awbd.mybarberapp.repositories.security.AuthorityRepository;
import com.awbd.mybarberapp.repositories.security.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
@Profile("mysql")
public class DataLoader implements CommandLineRunner {

    private AuthorityRepository authorityRepository;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;


    private void loadUserData() {
        if (userRepository.count() == 0){
            Authority adminRole = authorityRepository.save(Authority.builder().role("ROLE_BARBER").build());
            Authority guestRole = authorityRepository.save(Authority.builder().role("ROLE_CLIENT").build());

            User admin = User.builder()
                    .username("admin").email("admin@gmail.com")
                    .password(passwordEncoder.encode("12345"))
                    .authority(adminRole)
                    .build();

            User guest = User.builder()
                    .username("guest").email("guest@gmail.com")
                    .password(passwordEncoder.encode("12345"))
                    .authority(guestRole)
                    .build();

            userRepository.save(admin);
            userRepository.save(guest);
        }
    }


    @Override
    public void run(String... args) throws Exception {
        loadUserData();
    }
}