package com.qlda.sontay.service.project.impl;

import com.qlda.sontay.domain.project.Project;
import com.qlda.sontay.mapper.project.ProjectDTOMapper;
import com.qlda.sontay.repository.project.ProjectRepository;
import com.qlda.sontay.service.project.ProjectPhaseService;
import com.qlda.sontay.service.project.ProjectService;
import com.qlda.sontay.service.project.ProjectTaskService;
import com.qlda.sontay.dto.project.ProjectDTO;
import com.qlda.sontay.dto.project.ProjectPhaseDTO;
import com.qlda.sontay.dto.project.ProjectTaskDTO;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    private final ProjectPhaseService projectPhaseService;

    private final ProjectTaskService projectTaskService;

    private final ProjectDTOMapper projectDTOMapper;


    @Override
    public ProjectDTO create(ProjectDTO req) {
        List<ProjectPhaseDTO> phases = new ArrayList<>();

        // lưu db phase , task do mongo ko lưu được tự đôg giống java có (@OneToMany(cascade = CascadeType.ALL))
        if (req.getPhases() != null) {
            for (ProjectPhaseDTO phaseDTO : req.getPhases()) {

                List<ProjectTaskDTO> tasks = new ArrayList<>();
                if (phaseDTO.getTasks() != null) {
                    for (ProjectTaskDTO taskDTO : phaseDTO.getTasks()) {
                        tasks.add(projectTaskService.create(taskDTO));
                    }
                    phaseDTO.setTasks(tasks);
                    phases.add(projectPhaseService.create(phaseDTO));
                }
            }
        }

        req.setPhases(phases);

       Project project= projectRepository.save(projectDTOMapper.toEntity(req));

        return projectDTOMapper.toDto(project);
    }

    @Override
    public ProjectDTO findById(ObjectId id) {
        Project project = projectRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Project not found with id: " + id));
        return projectDTOMapper.toDto(project);
    }


    @Override
    public List<ProjectDTO> findAll() {
        List<Project> res = projectRepository.findAll();
        return projectDTOMapper.toDto(res);
    }

    @Override
    public Project update(ObjectId id, ProjectDTO dto) {
        Project existing = projectRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Project not found with id: " + id));

        projectDTOMapper.partialUpdate(existing, dto);
//
//        if (dto.getPhaseIds() != null && !dto.getPhaseIds().isEmpty()) {
//            var phases = projectPhaseRepository.findAllById(dto.getPhaseIds());
//            existing.setPhases(phases);
//        }

        existing.setUpdatedAt(new Date());

        return projectRepository.save(existing);
    }

    @Override
    public ObjectId delete(ObjectId id) {
        projectRepository.deleteById(id);
        return id;
    }
}
