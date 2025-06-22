package com.awbd.mybarberapp.controllers;

import com.awbd.mybarberapp.domain.Account;
import com.awbd.mybarberapp.domain.Appointment;
import com.awbd.mybarberapp.domain.AppointmentStatus;
import com.awbd.mybarberapp.dtos.AppointmentDTO;
import com.awbd.mybarberapp.dtos.BarberProcedureDTO;
import com.awbd.mybarberapp.repositories.HairProcedureRepository;
import com.awbd.mybarberapp.repositories.security.UserRepository;
import com.awbd.mybarberapp.services.AppointmentService;
import com.awbd.mybarberapp.services.BarberProcedureService;
import com.awbd.mybarberapp.services.security.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.security.core.Authentication;

import java.security.Principal;
import java.util.List;


@Controller
@RequestMapping("/barber")
@RequiredArgsConstructor
public class BarberController {

    private final BarberProcedureService procedureService;
    private final HairProcedureRepository hairProcedureRepository;
    private final UserRepository userRepository;
    private final AppointmentService appointmentService;

    @GetMapping("/home")
    public String barberHome(Model model, Principal principal) {
        String userName = principal.getName();
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Long barberId = user.getId();
        List<AppointmentDTO> createdAppointments = appointmentService
                .getAppointmentsByBarberAndStatus(barberId, "CREATED");
        System.out.println("LISTA PROGRAMARI:  "+createdAppointments.toString());
        model.addAttribute("createdAppointments", createdAppointments);
        return "barber/home";
    }

    @PostMapping("/appointments/{id}/status")
    public String updateAppointmentStatus(@PathVariable Long id,
                                          @RequestParam String status,
                                          Principal principal) {
        Appointment appointment = appointmentService.getById(id);
        appointment.setStatus(AppointmentStatus.valueOf(status.toUpperCase()));
        appointmentService.save(appointment);

        return "redirect:/barber/home"; // 🔁 Înapoi la homepage după acțiune
    }

    @GetMapping("/appointments/history")
    public String appointmentHistory(Model model, Principal principal) {
        String username = principal.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<AppointmentDTO> history = appointmentService.getPastAppointmentsForBarber(user.getId());

        model.addAttribute("historyAppointments", history);
        return "barber/history"; // barber/history.html
    }



    @GetMapping("/procedures")
    public String showProcedures(Model model, Authentication authentication) {
        String userName = authentication.getName();
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Long barberId = user.getId();
        model.addAttribute("procedures", procedureService.getAllForBarber(barberId));
        model.addAttribute("hairProcedures", hairProcedureRepository.findAll());
        model.addAttribute("form", new BarberProcedureDTO());
        return "barber/procedures";
    }

    @PostMapping("/procedures")
    public String addProcedure(@ModelAttribute("form") BarberProcedureDTO dto,
                               Authentication authentication) {
        String userName = authentication.getName();
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        dto.setBarberId(user.getId());
        System.out.println("🔍 FORM INPUT:");
        System.out.println("ID: " + dto.getId());
        System.out.println("ProcedureName: " + dto.getProcedureName());
        System.out.println("Price: " + dto.getPrice());
        System.out.println("BarberId: " + dto.getBarberId());
        procedureService.saveOrUpdate(dto);
        return "redirect:/barber/procedures";
    }

    @GetMapping("/procedures/edit/{id}")
    public String editProcedure(@PathVariable Long id, Model model, Principal principal, Authentication authentication) {
        String userName = authentication.getName();
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        System.out.println("DTP Barber" + user.getAuthorities());

        BarberProcedureDTO dto = procedureService.findById(id);
        System.out.println("DTP Barber" + dto.toString());
        if (!dto.getBarberId().equals(user.getId())) {
            throw new AccessDeniedException("Nu ai voie să editezi această procedură.");
        }

        model.addAttribute("form", dto);
        model.addAttribute("procedures", procedureService.getAllForBarber(user.getId()));
        model.addAttribute("hairProcedures", hairProcedureRepository.findAll());

        return "barber/procedures";
    }


    @GetMapping("/procedures/delete/{id}")
    public String deleteProcedure(@PathVariable Long id, Principal principal) {
        String name = principal.getName();
        User barber = userRepository.findByUsername(name)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        BarberProcedureDTO procedure = procedureService.findById(id);

        if (!procedure.getBarberId().equals(barber.getId())) {
            throw new AccessDeniedException("Nu poți șterge această procedură.");
        }

        procedureService.deleteById(id);
        return "redirect:/barber/procedures";
    }

}

