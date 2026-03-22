package com.example.todoapp.repository;

import com.example.todoapp.entity.Task;
import com.example.todoapp.enums.TaskPriority;
import com.example.todoapp.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByStatus(TaskStatus status);

    List<Task> findByPriority(TaskPriority priority);

    List<Task> findByStatusAndPriority(TaskStatus status, TaskPriority priority);

}
