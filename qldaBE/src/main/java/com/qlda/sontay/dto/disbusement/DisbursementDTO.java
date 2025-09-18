package com.qlda.sontay.dto.disbusement;

import com.qlda.sontay.common.DisbursementStatus;

import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DisbursementDTO {

    String id;

    String code;

    String projectId;
    String projectName;

    String period;

    List<DisbursementItemDTO> items;

    String note;

    List<String> milestones;

    Double advanceDeduction;

    Double completionPct;

    DisbursementStatus status;

    Date submittedAt;
    Date createdAt;
}
