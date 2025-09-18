package com.qlda.sontay.service.project.base;

import com.qlda.sontay.dto.project.ProjectPhaseDTO;
import org.bson.types.ObjectId;

import java.util.List;

public interface ProjectPhaseServiceBase {
    ProjectPhaseDTO create(ProjectPhaseDTO req);
    ProjectPhaseDTO findById(ObjectId projectPhaseId);
    List<ProjectPhaseDTO> findAll();
    ProjectPhaseDTO update(ObjectId projectPhaseId, ProjectPhaseDTO project);
    ObjectId delete(ObjectId projectPhaseId);
}
