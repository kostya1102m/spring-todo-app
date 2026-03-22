package com.example.todoapp.dto.response;

import java.time.LocalDateTime;

public record ProjectResponse(
        Long id,
        String name,
        String description,
        int taskCount,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
