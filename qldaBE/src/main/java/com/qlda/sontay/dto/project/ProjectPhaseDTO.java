package com.qlda.sontay.dto.project;


import com.qlda.sontay.common.TaskStatus;
import com.qlda.sontay.domain.embedded_model.ProjectPhaseDocument;
import com.qlda.sontay.dto.base.TimeFrameDTO;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProjectPhaseDTO extends TimeFrameDTO {

    String id;

    String name;

    String description;

    Integer order;

    TaskStatus taskStatus;

    List<ProjectPhaseDocument> documentProjectPhase;

    List<ProjectTaskDTO> tasks;

    String legalBasis;
}
