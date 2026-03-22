package com.example.todoapp.service;

import com.example.todoapp.dto.request.ProjectRequest;
import com.example.todoapp.dto.response.ProjectResponse;
import com.example.todoapp.entity.Project;
import com.example.todoapp.exception.DuplicateResourceException;
import com.example.todoapp.exception.ResourceNotFoundException;
import com.example.todoapp.mapper.ProjectMapper;
import com.example.todoapp.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    public List<ProjectResponse> getAllProjects() {
        return projectRepository.findAll()
                .stream()
                .map(projectMapper::toResponse)
                .toList();
    }

    public ProjectResponse getProjectById(Long id) {
        Project project = findProjectOrThrow(id);
        return projectMapper.toResponse(project);
    }

    @Transactional
    public ProjectResponse createProject(ProjectRequest request)
    {
        if (projectRepository.existsByName(request.name()))
            throw new DuplicateResourceException("Проект", "c названием", request.name());

        Project project = projectMapper.toEntity(request);
        Project saved = projectRepository.save(project);
        return projectMapper.toResponse(saved);
    }

    @Transactional
    public ProjectResponse updateProject(Long id, ProjectRequest request) {
        Project project = findProjectOrThrow(id);
        projectMapper.updateEntity(project, request);
        Project updated = projectRepository.save(project);
        return projectMapper.toResponse(project);
    }

    @Transactional
    public void deleteProject(Long id)
    {
        Project project = findProjectOrThrow(id);
        projectRepository.delete(project);
    }

    public Project findProjectOrThrow(Long id)
    {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Проект", id));
    }
}
