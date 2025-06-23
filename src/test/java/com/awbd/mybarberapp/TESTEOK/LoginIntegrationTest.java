package com.awbd.mybarberapp.TESTEOK;

import com.awbd.mybarberapp.controllers.LoginRedirectController;
import com.awbd.mybarberapp.controllers.MainController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Aici testăm:
 * 1) GET /login → view-ul "login"
 * 2) GET /login-success sub autentificare → redirect spre "/barber" sau "/client"
 */
@WebMvcTest(controllers = {MainController.class, LoginRedirectController.class})
@ActiveProfiles("h2")
@Import(LoginIntegrationTest.TestSecurityConfig.class)     // Încarcă un UserDetailsService in-memory
class LoginIntegrationTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    @DisplayName("GET /login-success cu rol BARBER → redirect /barber")
    @WithMockUser(username = "bob", roles = "BARBER")
    void whenLoginSuccessAndRoleBarber_thenRedirectToBarber() throws Exception {
        mockMvc.perform(get("/login-success"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/barber"));
    }

    @Test
    @DisplayName("GET /login-success cu rol CLIENT → redirect /client/")
    @WithMockUser(username = "alice", roles = "CLIENT")
    void whenLoginSuccessAndRoleClient_thenRedirectToClient() throws Exception {
        mockMvc.perform(get("/login-success"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/client"));
    }



    /**
     * În test folosim un UserDetailsService simplu, in-memory,
     * ca să nu avem de-a face cu MySQL sau JPA.
     */
    static class TestSecurityConfig {
        @org.springframework.context.annotation.Bean
        InMemoryUserDetailsManager userDetailsService() {
            // nu contează detaliile, atâta timp rolurile sunt prezente
            return new InMemoryUserDetailsManager(
                    User.withDefaultPasswordEncoder()
                            .username("bob").password("pass").roles("BARBER").build(),
                    User.withDefaultPasswordEncoder()
                            .username("alice").password("pass").roles("CLIENT").build()
            );
        }
    }
}
