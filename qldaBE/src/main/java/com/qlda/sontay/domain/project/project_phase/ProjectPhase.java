package com.qlda.sontay.domain.project.project_phase;
import com.qlda.sontay.domain.project.ProjectTask;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(collection = "cl_project_phase")
public class ProjectPhase extends ProjectPhaseBase {
    @Field("start_date")
    Date startDate;

    @Field("end_date")
    Date endDate;

    @DocumentReference
    List<ProjectTask> tasks;

}
