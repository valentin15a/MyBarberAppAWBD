package com.awbd.mybarberapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.Authentication;


@Controller
public class LoginRedirectController {

    @GetMapping("/login-success")
    public String redirectAfterLogin(Authentication authentication) {
        if (authentication == null) return "redirect:/login";

        var roles = authentication.getAuthorities();
        boolean isBarber = roles.stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_BARBER"));

        return isBarber ? "redirect:/barber/home" : "redirect:/";
    }
}

