package com.qlda.sontay.service.project;

import com.qlda.sontay.dto.project.ProjectTaskDTO;
import org.bson.types.ObjectId;

import java.util.List;

public interface ProjectTaskService {
    ProjectTaskDTO create(ProjectTaskDTO req);
    ProjectTaskDTO findById(ObjectId projectTaskId);
    List<ProjectTaskDTO> findAll();
    ObjectId delete(ObjectId projectTaskId);
    List<ProjectTaskDTO> createAll(List<ProjectTaskDTO> projectTaskDTOS);
}
