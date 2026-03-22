package com.example.todoapp.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProjectRequest(
        @NotBlank(message = "Необходимо указать имя проекта")
        @Size(max = 255, message = "Имя проекта должно состоять не более чем из 255 символов")
        String name,

        @Size(max = 1000, message = "Описание проекта должно состоять не более чем из 1000 символов")
        String description
) {}
