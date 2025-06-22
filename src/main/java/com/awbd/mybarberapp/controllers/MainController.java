package com.awbd.mybarberapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/")
public class MainController {


    @RequestMapping("")
    public String productForm() {
        return "main";
    }

    @GetMapping("/login")
    public String showLogInForm(){ return "login"; }

//    @GetMapping("/register")
//    public String showRegisterForm(){ return "login"; }

    @GetMapping("/access_denied")
    public String accessDeniedPage(){ return "accessDenied"; }

}