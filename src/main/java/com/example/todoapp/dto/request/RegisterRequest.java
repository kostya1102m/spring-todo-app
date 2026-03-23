package com.example.todoapp.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank(message = "Необходимо указать электронную почту")
        @Email(message = "Неверный формат почты")
        String email,

        @NotBlank(message = "Необходимо указать пароль")
        @Size(min = 6, max = 20, message = "Пароль должен состоять от 6 до 20 символов")
        String password,

        String firstName,
        String lastName
) {}
