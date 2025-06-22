package com.awbd.mybarberapp.controllers;

import com.awbd.mybarberapp.dtos.AppointmentDTO;
import com.awbd.mybarberapp.repositories.security.UserRepository;
import com.awbd.mybarberapp.services.AppointmentService;
import com.awbd.mybarberapp.services.security.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {

    private final AppointmentService appointmentService;
    private final UserRepository userRepository;

    @GetMapping("/home")
    public String clientHome(Model model, Principal principal) {
        String username = principal.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<AppointmentDTO> appointments = appointmentService.getByClientIdDto(user.getId());
        model.addAttribute("myAppointments", appointments);
        return "main";
    }
}
