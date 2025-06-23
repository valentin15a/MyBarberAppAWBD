package com.awbd.mybarberapp.TESTEOK;

import com.awbd.mybarberapp.domain.Authority;
import com.awbd.mybarberapp.domain.HairProcedure;
import com.awbd.mybarberapp.domain.Appointment;
import com.awbd.mybarberapp.repositories.security.AuthorityRepository;
import com.awbd.mybarberapp.repositories.security.UserRepository;
import com.awbd.mybarberapp.repositories.HairProcedureRepository;
import com.awbd.mybarberapp.repositories.AppointmentRepository;
import com.awbd.mybarberapp.services.security.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.util.Set;


import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.datasource.driverClassName=org.h2.Driver",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.sql.init.platform=h2"
})
@AutoConfigureMockMvc
@ActiveProfiles("h2")
@Import(TestSecurityConfig.class)
class AppointmentIntegrationTest {

    @Autowired MockMvc mockMvc;
    @Autowired AuthorityRepository authorityRepository;
    @Autowired UserRepository userRepository;
    @Autowired HairProcedureRepository hairProcedureRepository;
    @Autowired AppointmentRepository appointmentRepository;
    @Autowired PasswordEncoder passwordEncoder;
    private User client;
    private com.awbd.mybarberapp.services.security.User barber;
    private HairProcedure hp1, hp2;

    @BeforeEach
    void setUp() {
        // golim tabelele
        appointmentRepository.deleteAll();
        hairProcedureRepository.deleteAll();
        userRepository.deleteAll();
        authorityRepository.deleteAll();

        // roluri
        Authority clientRole = new Authority();
        clientRole.setRole("CLIENT");
        clientRole = authorityRepository.save(clientRole);

        Authority barberRole = new Authority();
        barberRole.setRole("BARBER");
        barberRole = authorityRepository.save(barberRole);

        // utilizator client
        client = new User();
        client.setEmail("cl3i@test.com");

        client.setPassword(passwordEncoder.encode("pass1"));
        client.setEnabled(true);
        client.setAccountNonExpired(true);
        client.setAccountNonLocked(true);
        client.setCredentialsNonExpired(true);
        client.setAuthorities(Set.of(clientRole));
        client.setUsername("Cli");
        client = userRepository.save(client);

        // utilizator barber
        barber = new com.awbd.mybarberapp.services.security.User();
        barber.setEmail("barb@test.com");
        barber.setPassword(passwordEncoder.encode("pass2"));
        barber.setEnabled(true);
        barber.setAccountNonExpired(true);
        barber.setAccountNonLocked(true);
        barber.setCredentialsNonExpired(true);
        barber.setAuthorities(Set.of(barberRole));
        barber.setUsername("barb");
        barber = userRepository.save(barber);

        // proceduri
        hp1 = new HairProcedure();
        hp1.setName("Cut");
        hp1 = hairProcedureRepository.save(hp1);

        hp2 = new HairProcedure();
        hp2.setName("Color");
        hp2 = hairProcedureRepository.save(hp2);
    }



    @Test
    @Transactional
    @DisplayName("CLIENT logat → POST creare programare în H2 și redirect success")
    void clientCreatesAppointment_inH2() throws Exception {

        MvcResult login= mockMvc.perform(formLogin("/perform_login")
                        .user("email", "cl3i@test.com")     // cheia trebuie să corespundă cu .usernameParameter("email")
                        .password("pass1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login-success")).andReturn();

        MockHttpSession session = (MockHttpSession) login
                .getRequest().getSession(false);

        // 2) POST creare programare
        mockMvc.perform(post("/client/appointments")
                        .session(session)
                        .with(csrf())
                        .param("barberId", String.valueOf(barber.getId()))
                        .param("date",     "2025-07-20")
                        .param("time",     "10:30")
                        .param("hairProcedureIds",
                                String.valueOf(hp1.getId()),
                                String.valueOf(hp2.getId()))
                        .param("totalPrice", "80.0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/client/appointments/success"));

        // 3) Verificăm ce a rămas în H2
        Appointment appt = appointmentRepository.findByClientId(client.getId()).get(0);
        appt.getProcedures().size(); // 👈 forțează încărcarea colecției



        assertThat(appt.getBarberId()).isEqualTo(barber.getId());
        assertThat(appt.getDate()).isEqualTo(LocalDate.of(2025,7,20));
        assertThat(appt.getTime()).isEqualTo("10:30");
        assertThat(appt.getPrice()).isEqualTo(80.0);
        assertThat(appt.getProcedures())
                .extracting(HairProcedure::getId)
                .containsExactlyInAnyOrder(hp1.getId(), hp2.getId());

    }

}
