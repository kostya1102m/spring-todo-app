package com.example.todoapp.controller;

import com.example.todoapp.dto.request.TaskCreateRequest;
import com.example.todoapp.dto.request.TaskUpdateRequest;
import com.example.todoapp.dto.response.TaskResponse;
import com.example.todoapp.entity.Task;
import com.example.todoapp.enums.TaskStatus;
import com.example.todoapp.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAllTasks
            (
            @RequestParam(required = false)TaskStatus status
            )
    {
        List<TaskResponse> tasks;

        if (status != null)
            tasks = taskService.getTaskByStatus(status);
        else
            tasks = taskService.getAllTasks();

        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable Long id)
    {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @PostMapping
    public ResponseEntity<TaskResponse> createTask
            (
            @Valid @RequestBody TaskCreateRequest request
            )
    {
        TaskResponse created = taskService.createTask(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask
            (
                    @PathVariable Long id,
                    @Valid @RequestBody TaskUpdateRequest request
            )
    {
        return ResponseEntity.ok(taskService.updateTask(id, request));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TaskResponse> updateTaskStatus
            (
                    @PathVariable Long id,
                    @RequestParam TaskStatus status
            )
    {
        return ResponseEntity.ok(taskService.updateTaskStatus(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TaskResponse> deleteTask(@PathVariable Long id)
    {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
