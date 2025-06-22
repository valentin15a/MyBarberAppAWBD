package com.awbd.mybarberapp.controllers;

import com.awbd.mybarberapp.dtos.BarberScheduleDTO;
import com.awbd.mybarberapp.repositories.security.UserRepository;
import com.awbd.mybarberapp.services.BarberScheduleService;
import com.awbd.mybarberapp.services.security.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.security.core.Authentication;

import java.time.DayOfWeek;
import java.util.List;

@Controller
@RequestMapping("/barber/schedule")
@RequiredArgsConstructor
public class BarberScheduleController {

    private final BarberScheduleService scheduleService;
    private final UserRepository userRepository;

    @GetMapping
    public String showSchedule(Model model, Authentication authentication) {
        String userName = authentication.getName();
        User user = userRepository.findByEmail(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<BarberScheduleDTO> schedule = scheduleService.getAllSchedulesForBarber(user.getId());

        model.addAttribute("schedule", schedule);
        model.addAttribute("form", new BarberScheduleDTO());
        return "barber/schedule"; // ← view HTML
    }

    @PostMapping
    public String saveSchedule(@ModelAttribute("form") BarberScheduleDTO dto,
                               Authentication authentication) {
        String userName = authentication.getName();
        User user = userRepository.findByEmail(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        dto.setBarberId(user.getId());
        scheduleService.saveOrUpdate(dto);

        return "redirect:/barber/schedule";
    }

    @GetMapping("/{day}")
    @ResponseBody
    public BarberScheduleDTO getScheduleForDay(@PathVariable DayOfWeek day,
                                               Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return scheduleService.getScheduleForBarberAndDay(user.getId(), day);
    }

    @GetMapping("/edit/{day}")
    public String editScheduleForDay(@PathVariable DayOfWeek day,
                                     Model model,
                                     Authentication authentication) {
        String userName = authentication.getName();
        User user = userRepository.findByEmail(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<BarberScheduleDTO> schedule = scheduleService.getAllSchedulesForBarber(user.getId());
        BarberScheduleDTO form = scheduleService.getScheduleForBarberAndDay(user.getId(), day);

        model.addAttribute("schedule", schedule);
        model.addAttribute("form", form);
        return "barber/schedule";
    }


}
