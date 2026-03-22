package com.example.todoapp.service;

import com.example.todoapp.dto.request.TaskCreateRequest;
import com.example.todoapp.dto.request.TaskUpdateRequest;
import com.example.todoapp.dto.response.PagedResponse;
import com.example.todoapp.dto.response.TaskResponse;
import com.example.todoapp.entity.Project;
import com.example.todoapp.entity.Task;
import com.example.todoapp.enums.TaskPriority;
import com.example.todoapp.enums.TaskStatus;
import com.example.todoapp.exception.ResourceNotFoundException;
import com.example.todoapp.mapper.TaskMapper;
import com.example.todoapp.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final ProjectService projectService;

    public PagedResponse<TaskResponse> getAllTasks(
            TaskStatus status,
            TaskPriority priority,
            Long projectId,
            int page,
            int size,
            String sortBy,
            String direction
    )
    {
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Task> taskPage = taskRepository.findWithFilters(
                status, priority, projectId, pageable
        );

        return toPagedResponse(taskPage);
    }

    public TaskResponse getTaskById(Long id)
    {
        Task task = findTaskOrThrow(id);
        return taskMapper.toResponse(task);
    }

    public List<TaskResponse> getTaskByStatus(TaskStatus status)
    {
        return taskRepository.findByStatus(status)
                .stream()
                .map(taskMapper::toResponse)
                .toList();
    }

    @Transactional
    public TaskResponse createTask(TaskCreateRequest request)
    {
        Task task = taskMapper.toEntity(request);

        if (request.projectId() != null)
        {
            Project project = projectService.findProjectOrThrow(request.projectId());
            task.setProject(project);
        }
        else
            task.setProject(null);

        Task savedTask = taskRepository.save(task);
        return taskMapper.toResponse(savedTask);
    }

    @Transactional
    public TaskResponse updateTask(Long id, TaskUpdateRequest request)
    {
        Task task = findTaskOrThrow(id);
        taskMapper.updateEntity(task, request);
        Task updatedTask = taskRepository.save(task);
        return taskMapper.toResponse(updatedTask);
    }

    @Transactional
    public TaskResponse updateTaskStatus(Long id, TaskStatus status)
    {
        Task task = findTaskOrThrow(id);
        task.setStatus(status);
        Task updatedTask = taskRepository.save(task);
        return taskMapper.toResponse(updatedTask);
    }

    @Transactional
    public void deleteTask(Long id)
    {
        Task task = findTaskOrThrow(id);
        taskRepository.delete(task);
    }


    private Task findTaskOrThrow(Long id)
    {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Задача", id));
    }

    private PagedResponse<TaskResponse> toPagedResponse(Page<Task> page) {
        return new PagedResponse<>(
                page.getContent().stream()
                        .map(taskMapper::toResponse)
                        .toList(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()
        );
    }

}
