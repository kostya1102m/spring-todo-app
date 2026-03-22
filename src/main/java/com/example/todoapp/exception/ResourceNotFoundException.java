package com.example.todoapp.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resource, Long id) {
        super(resource + " с id " + id + " не найден ");
    }
}
