package com.example.todoapp.controller;

import com.example.todoapp.dto.request.ProjectRequest;
import com.example.todoapp.dto.response.PagedResponse;
import com.example.todoapp.dto.response.ProjectResponse;
import com.example.todoapp.dto.response.TaskResponse;
import com.example.todoapp.service.ProjectService;
import com.example.todoapp.service.TaskService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Проекты", description = "Управление проектами")
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
public class ProjectContoller {

    private final ProjectService projectService;
    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<List<ProjectResponse>> getAllProjects()
    {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponse> getProjectById(@PathVariable Long id)
    {
        return ResponseEntity.ok(projectService.getProjectById(id));
    }

    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(
            @Valid @RequestBody ProjectRequest request
    )
    {
        ProjectResponse created = projectService.createProject(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponse> updateProject(
            @PathVariable Long id,
            @Valid @RequestBody ProjectRequest request
    )
    {
        return ResponseEntity.ok(projectService.updateProject(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProjectResponse> delteProject(@PathVariable Long id)
    {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/tasks")
    public ResponseEntity<PagedResponse<TaskResponse>> getProjectTasks(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        PagedResponse<TaskResponse> tasks = taskService.getAllTasks(
                null, null, id, page, size, sortBy, direction
        );
        return ResponseEntity.ok(tasks);
    }
}
