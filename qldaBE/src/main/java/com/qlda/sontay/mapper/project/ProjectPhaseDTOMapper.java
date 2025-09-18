package com.qlda.sontay.mapper.project;

import com.qlda.sontay.domain.project.project_phase.ProjectPhase;
import com.qlda.sontay.mapper.base.EntityMapper;
import com.qlda.sontay.mapper.base.ObjectIdMapper;
import com.qlda.sontay.dto.project.ProjectPhaseDTO;
import org.mapstruct.*;

@Mapper(
    componentModel = "spring",
    uses = {ObjectIdMapper.class, ProjectTaskDTOMapper.class }
)
public interface ProjectPhaseDTOMapper extends EntityMapper<ProjectPhaseDTO, ProjectPhase> {

    @Override
    @Mapping(source = "tasks", target = "tasks")
    @Mapping(source = "id", target = "id")
    ProjectPhase toEntity(ProjectPhaseDTO dto);

    @Override
    @Mapping(source = "tasks", target = "tasks")
    @Mapping(source = "id", target = "id")
    ProjectPhaseDTO toDto(ProjectPhase entity);
}
