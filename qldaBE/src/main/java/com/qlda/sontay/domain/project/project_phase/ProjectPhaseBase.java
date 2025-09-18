package com.qlda.sontay.domain.project.project_phase;

import com.qlda.sontay.common.TaskStatus;
import com.qlda.sontay.domain.base.BaseEntity;
import com.qlda.sontay.domain.embedded_model.ProjectPhaseDocument;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PROTECTED)
public abstract class ProjectPhaseBase extends BaseEntity {

    @Field("name")
    String name;

    @Field("description")
    String description;

    @Field("order")
    Integer order;

    @Field("task_status")
    TaskStatus taskStatus;

    @Field("legal_basis")
    String legalBasis;

    @Field("document_project_phase")
    List<ProjectPhaseDocument> documentProjectPhase;
}
