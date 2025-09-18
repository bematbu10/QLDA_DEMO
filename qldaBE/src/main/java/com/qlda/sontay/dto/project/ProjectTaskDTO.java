package com.qlda.sontay.dto.project;

import com.qlda.sontay.common.Priority;
import com.qlda.sontay.common.TaskStatus;
import com.qlda.sontay.domain.embedded_model.BaseDocument;
import com.qlda.sontay.dto.base.TimeFrameDTO;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProjectTaskDTO extends TimeFrameDTO {

    @Builder.Default
    String id = new ObjectId().toHexString();

    String name;

    String description;

    TaskStatus taskStatus;

    Priority priority;

    String assignee;

    Double progress;

    String assignUser;

    List<String> dependencies;

    List<BaseDocument> documentsTask;

    String legalBasis;
}
