package com.example.todoapp.mapper;


import com.example.todoapp.dto.request.TaskCreateRequest;
import com.example.todoapp.dto.request.TaskUpdateRequest;
import com.example.todoapp.dto.response.TaskResponse;
import com.example.todoapp.entity.Task;
import com.example.todoapp.enums.TaskStatus;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public Task toEntity(TaskCreateRequest request){
        return Task.builder()
                .title(request.title())
                .description(request.description())
                .priority(request.priority())
                .status(TaskStatus.TODO)
                .deadline(request.deadline())
                .build();
    }

    public void updateEntity(Task task, TaskUpdateRequest request){
        task.setTitle(request.title());
        task.setDescription(request.description());
        task.setStatus(request.status());
        task.setPriority(request.priority());
        task.setDeadline(request.deadline());
    }

    public TaskResponse toResponse(Task task){
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getPriority(),
                task.getDeadline(),
                task.getCreatedAt(),
                task.getUpdatedAt()
        );
    }
}
