package com.qlda.sontay.mapper.project;


import com.qlda.sontay.domain.project.project_phase.ProjectPhaseCategory;
import com.qlda.sontay.mapper.base.EntityMapper;
import com.qlda.sontay.mapper.base.ObjectIdMapper;
import com.qlda.sontay.dto.project.ProjectPhaseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    componentModel = "spring",
    uses = {ObjectIdMapper.class}
)
public interface ProjectPhaseCategoryDTOMapper extends EntityMapper<ProjectPhaseDTO, ProjectPhaseCategory> {

    @Override
    @Mapping(source = "id", target = "id")
    ProjectPhaseCategory toEntity(ProjectPhaseDTO dto);

    @Override
    @Mapping(source = "id", target = "id")
    ProjectPhaseDTO toDto(ProjectPhaseCategory entity);
}
