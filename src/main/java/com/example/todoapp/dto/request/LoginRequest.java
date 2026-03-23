package com.example.todoapp.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(

        @NotBlank(message = "Необходимо указать электронную почту")
        @Email(message = "Неверный формат почты")
        String email,

        @NotBlank(message = "Необходимо указать пароль")
        String password
) {}