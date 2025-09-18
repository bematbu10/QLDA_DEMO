package com.qlda.sontay.service.project.impl;

import com.qlda.sontay.domain.project.project_phase.ProjectPhase;
import com.qlda.sontay.dto.project.ProjectPhaseDTO;
import com.qlda.sontay.mapper.project.ProjectPhaseDTOMapper;
import com.qlda.sontay.repository.project.ProjectPhaseRepository;
import com.qlda.sontay.service.project.ProjectPhaseService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectPhaseServiceImpl implements ProjectPhaseService {

    private final ProjectPhaseRepository projectPhaseRepository;

    private final ProjectPhaseDTOMapper projectPhaseDTOMapper;

    @Override
    public ProjectPhaseDTO create(ProjectPhaseDTO req) {
         ProjectPhase projectPhase =
             projectPhaseRepository.save(projectPhaseDTOMapper.toEntity(req));
        return projectPhaseDTOMapper.toDto(projectPhaseRepository.save(projectPhase));
    }

    @Override
    public ProjectPhaseDTO findById(ObjectId projectPhaseId) {
        var projectPhase = projectPhaseRepository.findById(projectPhaseId)
            .orElseThrow(() -> new RuntimeException("projectPhase not found with id: " + projectPhaseId));
        return projectPhaseDTOMapper.toDto(projectPhase);
    }
    @Override
    public List<ProjectPhaseDTO> findAll() {
        return projectPhaseDTOMapper.toDto(projectPhaseRepository.findAll());
    }

    @Override
    public ProjectPhaseDTO update(ObjectId projectPhaseId, ProjectPhaseDTO dto) {
        ProjectPhase existing = projectPhaseRepository.findById(projectPhaseId)
            .orElseThrow(() -> new RuntimeException("Project not found with id: " + projectPhaseId));

        projectPhaseDTOMapper.partialUpdate(existing, dto);

        return projectPhaseDTOMapper.toDto(projectPhaseRepository.save(existing));
    }

    @Override
    public ObjectId delete(ObjectId projectPhaseId) {
        projectPhaseRepository.deleteById(projectPhaseId);
        return projectPhaseId;
    }

}
