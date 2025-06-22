package com.awbd.mybarberapp.controllers;
import com.awbd.mybarberapp.domain.Appointment;
import com.awbd.mybarberapp.domain.AppointmentStatus;
import com.awbd.mybarberapp.domain.BarberProcedure;
import com.awbd.mybarberapp.domain.HairProcedure;
import com.awbd.mybarberapp.services.HairProcedureService;
import org.springframework.format.annotation.DateTimeFormat;
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

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final UserRepository userRepository;
    private final AppointmentService appointmentService;
    private final BarberProcedureService procedureService;
    private final HairProcedureService hairProcedureService;

    @GetMapping("/new")
    public String showForm(@RequestParam(required = false) Long barberId,
                           @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                           Model model) {
        List<User> barbers = userRepository.findAllByAuthorityRole("ROLE_BARBER");
        model.addAttribute("barbers", barbers);
        model.addAttribute("appointmentDTO", new AppointmentDTO());

        if (barberId != null) {
            model.addAttribute("selectedBarberId", barberId);
            model.addAttribute("procedures", procedureService.getAllForBarber(barberId));

            System.out.println("Proceduri selectate: " + procedureService.getAllForBarber(barberId));
        }

        if (barberId != null && date != null) {
            List<String> availableHours = appointmentService.getAvailableHoursForBarber(barberId, date);
            model.addAttribute("availableSlots", availableHours);
        }

        return "appointments/form";
    }
    @GetMapping("/available-hours")
    @ResponseBody
    public List<String> getAvailableHours(@RequestParam Long barberId,
                                          @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return appointmentService.getAvailableHoursForBarber(barberId, date);
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
        List<HairProcedure> selectedProcedures = dto.getHairProcedureIds()
                .stream()
                .map(hairProcedureService::findById) //
                .toList();



        // Calculează preț total
        Double priceObj = dto.getTotalPrice();
        double total = priceObj != null ? priceObj : 0.0;



        // Creează și salvează programarea
        Appointment appointment = Appointment.builder()
                .barberId(dto.getBarberId())
                .clientId(clientId)
                .date(dto.getDate())
                .time(dto.getTime())
                .procedures(selectedProcedures)
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
