package com.qlda.sontay.service.project;

import com.qlda.sontay.domain.project.Project;
import com.qlda.sontay.dto.project.ProjectDTO;
import org.bson.types.ObjectId;

import java.util.List;

public interface ProjectService {
    ProjectDTO create(ProjectDTO req);
    ProjectDTO findById(ObjectId id);
    List<ProjectDTO> findAll();
    Project update(ObjectId id, ProjectDTO project);
    ObjectId delete(ObjectId id);
}
