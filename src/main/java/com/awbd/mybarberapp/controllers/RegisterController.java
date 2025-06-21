package com.awbd.mybarberapp.controllers;

import com.awbd.mybarberapp.dtos.AccountDTO;
import com.awbd.mybarberapp.services.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class RegisterController {

    private final AccountService accountService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new AccountDTO());
        return "register";
    }

    @PostMapping("/register")
    public String processRegistration(@Valid @ModelAttribute("user") AccountDTO dto,
                                      BindingResult result,
                                      Model model) {
        if (result.hasErrors()) {
            return "register";
        }

        try {
            accountService.registerAccount(dto);
        } catch (IllegalArgumentException e) {
            model.addAttribute("registrationError", e.getMessage());
            return "register";
        }

        return "redirect:/login?success";
    }
}

