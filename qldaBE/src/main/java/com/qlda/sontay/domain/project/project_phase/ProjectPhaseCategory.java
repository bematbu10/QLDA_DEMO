package com.qlda.sontay.domain.project.project_phase;

import com.qlda.sontay.domain.project.ProjectTask;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(collection = "cl_project_phase_category")
public class ProjectPhaseCategory extends ProjectPhaseBase{

    @Field("project_tasks")
    List<ProjectTask> tasks;
}
