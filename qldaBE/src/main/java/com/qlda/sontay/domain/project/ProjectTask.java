package com.qlda.sontay.domain.project;
import com.qlda.sontay.common.Priority;
import com.qlda.sontay.common.TaskStatus;
import com.qlda.sontay.domain.base.TimeFrameEntity;
import com.qlda.sontay.domain.embedded_model.BaseDocument;
import lombok.*;
import lombok.experimental.FieldDefaults;

import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(collection = "cl_project_task")
public class ProjectTask extends TimeFrameEntity {

    @Field("name")
    String name;

    @Field("description")
    String description;

    @Field("taskStatus")
    TaskStatus taskStatus;

    @Field("priority")
    Priority priority;

    @Field("assignee")
    String assignee;

    @Field("progress")
    Double progress;

    @Field("assign_user")
    String assignUser;

    @Field("dependencies")
    List<String> dependencies;

    @Field("documents_task")
    List<BaseDocument> documentsTask;

    @Field("legal_basis")
    String legalBasis;
}
