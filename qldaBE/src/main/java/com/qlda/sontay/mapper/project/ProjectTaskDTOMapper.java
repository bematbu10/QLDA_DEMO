package com.qlda.sontay.mapper.project;

import com.qlda.sontay.domain.project.ProjectTask;
import com.qlda.sontay.mapper.base.EntityMapper;
import com.qlda.sontay.mapper.base.ObjectIdMapper;
import com.qlda.sontay.dto.project.ProjectTaskDTO;

import org.mapstruct.*;

@Mapper(componentModel = "spring",uses = {ObjectIdMapper.class})
public interface ProjectTaskDTOMapper extends EntityMapper<ProjectTaskDTO, ProjectTask> {
    @Override
    @Mapping(source = "id", target = "id")
    ProjectTask toEntity(ProjectTaskDTO dto);

    @Override
    @Mapping(source = "id", target = "id")
    ProjectTaskDTO toDto(ProjectTask entity);
}
