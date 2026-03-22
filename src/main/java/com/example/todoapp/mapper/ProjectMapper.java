package com.example.todoapp.mapper;


import com.example.todoapp.dto.request.ProjectRequest;
import com.example.todoapp.dto.response.ProjectResponse;
import com.example.todoapp.entity.Project;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper {

    public Project toEntity(ProjectRequest request)
    {
        return Project.builder()
                .name(request.name())
                .description(request.description())
                .build();
    }

    public void updateEntity(Project project, ProjectRequest request)
    {
        project.setName(request.name());
        project.setDescription(request.description());
    }

    public ProjectResponse toResponse(Project project)
    {
        return new ProjectResponse(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getTasks().size(),
                project.getCreatedAt(),
                project.getUpdatedAt()
        );
    }
}
