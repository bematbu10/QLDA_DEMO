package com.qlda.sontay.repository.project;

import com.qlda.sontay.domain.project.project_phase.ProjectPhaseCategory;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectPhaseCategoryRepository extends MongoRepository<ProjectPhaseCategory, ObjectId> {
}
