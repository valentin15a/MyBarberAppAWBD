package com.awbd.mybarberapp.controllers;

import com.awbd.mybarberapp.dtos.AppointmentDTO;
import com.awbd.mybarberapp.repositories.security.UserRepository;
import com.awbd.mybarberapp.services.AppointmentService;
import com.awbd.mybarberapp.services.security.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {

    private final AppointmentService appointmentService;
    private final UserRepository userRepository;

    @GetMapping({"/", ""})
    public String clientHome(@RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "5") int size,
                             @RequestParam(defaultValue = "date") String sortField,
                             @RequestParam(defaultValue = "desc") String sortDir,
                             Model model, Principal principal) {

        String username = principal.getName();
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Page<AppointmentDTO> apptPage = appointmentService.getByClientIdPaginated(user.getId(), page, size, sortField, sortDir);

        model.addAttribute("myAppointments", apptPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", apptPage.getTotalPages());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        return "main";
    }

}
