package com.awbd.mybarberapp.dtos;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AccountDTO {
    @NotBlank(message = "Username-ul este obligatoriu")
    private String username;

    @NotBlank(message = "Emailul este obligatoriu")
    @Email(message = "Format de email invalid")
    private String email;

    @NotBlank(message = "Parola este obligatorie")
    private String password;

    @NotBlank(message = "Confirmarea parolei este obligatorie")
    private String confirmPassword;

    @NotBlank(message = "Rolul este obligatoriu")
    private String role;
}
