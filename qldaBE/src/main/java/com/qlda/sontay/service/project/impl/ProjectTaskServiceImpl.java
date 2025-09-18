package com.qlda.sontay.service.project.impl;

import com.qlda.sontay.domain.project.ProjectTask;
import com.qlda.sontay.mapper.project.ProjectTaskDTOMapper;
import com.qlda.sontay.repository.project.ProjectTaskRepository;
import com.qlda.sontay.service.project.ProjectTaskService;
import com.qlda.sontay.dto.project.ProjectTaskDTO;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectTaskServiceImpl implements ProjectTaskService {

    private final ProjectTaskRepository projectTaskRepository;

    private final ProjectTaskDTOMapper projectTaskDTOMapper;

    @Override
    public ProjectTaskDTO create(ProjectTaskDTO req) {
        ProjectTask projectPhase = projectTaskDTOMapper.toEntity(req);
        return projectTaskDTOMapper.toDto(projectTaskRepository.save(projectPhase));
    }

    @Override
    public List<ProjectTaskDTO> createAll(List<ProjectTaskDTO> projectTaskDTOS) {
        if (projectTaskDTOS == null || projectTaskDTOS.isEmpty()) {
            return List.of();
        }

        List<ProjectTask> projectTasks = projectTaskDTOMapper.toEntity(projectTaskDTOS);

        if (projectTasks == null || projectTasks.isEmpty()) {
            return List.of();
        }

        List<ProjectTask> savedTasks = projectTaskRepository.saveAll(projectTasks);
        return projectTaskDTOMapper.toDto(savedTasks);
    }

    @Override
    public ProjectTaskDTO findById(ObjectId projectTaskId) {
        var projectPhase = projectTaskRepository.findById(projectTaskId)
            .orElseThrow(() -> new RuntimeException("projectPhase not found with id: " + projectTaskId));
        return projectTaskDTOMapper.toDto(projectPhase);
    }


    @Override
    public List<ProjectTaskDTO> findAll() {
        return projectTaskDTOMapper.toDto(projectTaskRepository.findAll());
    }


    @Override
    public ObjectId delete(ObjectId projectTaskId) {
        projectTaskRepository.deleteById(projectTaskId);
        return projectTaskId;
    }
}
