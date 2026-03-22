package com.example.todoapp.service;

import com.example.todoapp.dto.request.TaskCreateRequest;
import com.example.todoapp.dto.request.TaskUpdateRequest;
import com.example.todoapp.dto.response.TaskResponse;
import com.example.todoapp.entity.Task;
import com.example.todoapp.enums.TaskStatus;
import com.example.todoapp.exception.ResourceNotFoundException;
import com.example.todoapp.mapper.TaskMapper;
import com.example.todoapp.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public List<TaskResponse> getAllTasks() {
        return taskRepository.findAll()
                .stream()
                .map(taskMapper::toResponse)
                .toList();
    }

    public TaskResponse getTaskById(Long id) {
        Task task = findTaskOrThrow(id);
        return taskMapper.toResponse(task);
    }

    public List<TaskResponse> getTaskByStatus(TaskStatus status) {
        return taskRepository.findByStatus(status)
                .stream()
                .map(taskMapper::toResponse)
                .toList();
    }

    @Transactional
    public TaskResponse createTask(TaskCreateRequest request) {
        Task task = taskMapper.toEntity(request);
        Task savedTask = taskRepository.save(task);
        return taskMapper.toResponse(savedTask);
    }

    @Transactional
    public TaskResponse updateTask(Long id, TaskUpdateRequest request) {
        Task task = findTaskOrThrow(id);
        taskMapper.updateEntity(task, request);
        Task updatedTask = taskRepository.save(task);
        return taskMapper.toResponse(updatedTask);
    }

    @Transactional
    public TaskResponse updateTaskStatus(Long id, TaskStatus status) {
        Task task = findTaskOrThrow(id);
        task.setStatus(status);
        Task updatedTask = taskRepository.save(task);
        return taskMapper.toResponse(updatedTask);
    }

    @Transactional
    public void deleteTask(Long id) {
        Task task = findTaskOrThrow(id);
        taskRepository.delete(task);
    }


    private Task findTaskOrThrow(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Задача", id));
    }

}
