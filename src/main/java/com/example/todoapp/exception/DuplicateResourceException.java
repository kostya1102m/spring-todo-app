package com.example.todoapp.exception;

public class DuplicateResourceException extends RuntimeException {
    public DuplicateResourceException(String resource, String field, String value)
    {
        super(resource + "с" + field + " '" + value + "' уже существует");
    }
}
