package com.example.todoapp.dto.request;

import com.example.todoapp.enums.TaskPriority;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record TaskCreateRequest(
        @NotBlank(message = "Необходимо указать заголовок")
        @Size(max = 255, message = "Заголовок должен состоять не более чем из 255 символов")
        String title,

        @Size(max = 1000, message = "Описание должно состоять не более чем из 1000 символов")
        String description,

        @NotNull(message = "Необходимо указать приоритет")
        TaskPriority priority,

        @Future(message = "Дедлайн должен быть указан не раньше текущей даты")
        LocalDateTime deadline
) {}
