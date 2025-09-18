package com.qlda.sontay.service.project.impl;

import com.qlda.sontay.domain.project.project_phase.ProjectPhaseCategory;
import com.qlda.sontay.mapper.project.ProjectPhaseCategoryDTOMapper;
import com.qlda.sontay.repository.project.ProjectPhaseCategoryRepository;
import com.qlda.sontay.service.project.ProjectPhaseServiceCategory;
import com.qlda.sontay.dto.project.ProjectPhaseDTO;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectPhaseCategoryServiceImpl implements ProjectPhaseServiceCategory {

    private final ProjectPhaseCategoryRepository projectPhaseCategoryRepository;

    private final ProjectPhaseCategoryDTOMapper projectPhaseCategoryDTOMapper;

    @Override
    public ProjectPhaseDTO create(ProjectPhaseDTO req) {
         ProjectPhaseCategory projectPhaseCategory =
             projectPhaseCategoryRepository.save(projectPhaseCategoryDTOMapper.toEntity(req));
        return projectPhaseCategoryDTOMapper.toDto(projectPhaseCategoryRepository.save(projectPhaseCategory));
    }

    @Override
    public ProjectPhaseDTO findById(ObjectId projectPhaseId) {
        var projectPhase = projectPhaseCategoryRepository.findById(projectPhaseId)
            .orElseThrow(() -> new RuntimeException("projectPhase not found with id: " + projectPhaseId));
        return projectPhaseCategoryDTOMapper.toDto(projectPhase);
    }
    @Override
    public List<ProjectPhaseDTO> findAll() {
        return projectPhaseCategoryDTOMapper.toDto(projectPhaseCategoryRepository.findAll());
    }

    @Override
    public ProjectPhaseDTO update(ObjectId projectPhaseId, ProjectPhaseDTO dto) {
        ProjectPhaseCategory existing = projectPhaseCategoryRepository.findById(projectPhaseId)
            .orElseThrow(() -> new RuntimeException("Project not found with id: " + projectPhaseId));

        projectPhaseCategoryDTOMapper.partialUpdate(existing, dto);
       ;
        return projectPhaseCategoryDTOMapper.toDto(projectPhaseCategoryRepository.save(existing));
    }

    @Override
    public ObjectId delete(ObjectId projectPhaseId) {
        projectPhaseCategoryRepository.deleteById(projectPhaseId);
        return projectPhaseId;
    }

}
