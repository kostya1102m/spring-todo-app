package com.example.todoapp.dto.response;

public record AuthResponse(
        String token,
        String email,
        String firstName,
        String role
) {}