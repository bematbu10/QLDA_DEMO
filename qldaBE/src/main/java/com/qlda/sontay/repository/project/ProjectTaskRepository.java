package com.qlda.sontay.repository.project;

import com.qlda.sontay.domain.project.ProjectTask;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectTaskRepository extends MongoRepository<ProjectTask, ObjectId> {
}
