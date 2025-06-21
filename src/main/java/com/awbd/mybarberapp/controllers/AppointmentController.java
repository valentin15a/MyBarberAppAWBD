package com.awbd.mybarberapp.controllers;
import com.awbd.mybarberapp.domain.Appointment;
import com.awbd.mybarberapp.domain.AppointmentStatus;
import com.awbd.mybarberapp.domain.BarberProcedure;
import org.springframework.security.core.Authentication;
import com.awbd.mybarberapp.dtos.AppointmentDTO;
import com.awbd.mybarberapp.repositories.security.UserRepository;
import com.awbd.mybarberapp.services.AppointmentService;
import com.awbd.mybarberapp.services.BarberProcedureService;
import com.awbd.mybarberapp.services.security.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final UserRepository userRepository;
    private final AppointmentService appointmentService;
    private final BarberProcedureService procedureService;

    @GetMapping("/new")
    public String showForm(@RequestParam(required = false) Long barberId, Model model) {
        List<User> barbers = userRepository.findAllByAuthorityRole("ROLE_BARBER");
        model.addAttribute("barbers", barbers);
        model.addAttribute("appointmentDTO", new AppointmentDTO());

        if (barberId != null) {
            model.addAttribute("selectedBarberId", barberId);
            model.addAttribute("procedures", procedureService.getAllForBarber(barberId));
        }

        return "appointments/form";
    }

    @PostMapping
    public String createAppointment(@ModelAttribute AppointmentDTO dto,
                                    Authentication authentication,
                                    RedirectAttributes redirectAttributes) {

        // Obține utilizatorul curent
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Long clientId = user.getId();

        // Obține toate serviciile selectate
        List<BarberProcedure> selectedProcedures = dto.getBarberProcedureIds()
                .stream()
                .map(procedureService::findEntityById)
                .toList();

        // Calculează preț total
        double total = selectedProcedures.stream()
                .mapToDouble(BarberProcedure::getPrice)
                .sum();

        dto.setTotalPrice(total);

        // Creează și salvează programarea
        Appointment appointment = Appointment.builder()
                .barberId(dto.getBarberId())
                .clientId(clientId)
                .date(dto.getDate())
                .time(dto.getTime())
                .services(selectedProcedures)
                .price(total)
                .status(AppointmentStatus.CREATED)
                .build();

        appointmentService.save(appointment);
        return "redirect:/appointments/success";
    }

    @GetMapping("/success")
    public String showSuccess() {
        return "appointments/success";
    }
}
