package com.qlda.sontay.mapper.project;

import com.qlda.sontay.domain.project.Project;
import com.qlda.sontay.mapper.base.EntityMapper;
import com.qlda.sontay.mapper.base.ObjectIdMapper;
import com.qlda.sontay.dto.project.ProjectDTO;
import org.mapstruct.Mapper;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        uses = {ObjectIdMapper.class, ProjectPhaseDTOMapper.class } // dùng mapper của phase
)
public interface ProjectDTOMapper extends EntityMapper<ProjectDTO, Project> {
    @Override
    @Mapping(source = "phases", target = "phases")
    @Mapping(source = "id", target = "id")   // map list Phase <-> PhaseDTO
    Project toEntity(ProjectDTO dto);

    @Override
    @Mapping(source = "phases", target = "phases")
    @Mapping(source = "id", target = "id")
    ProjectDTO toDto(Project entity);

    @Named("partialUpdate")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(@MappingTarget Project entity, ProjectDTO dto);
}
