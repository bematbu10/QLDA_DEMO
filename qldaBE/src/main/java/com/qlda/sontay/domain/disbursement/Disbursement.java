package com.qlda.sontay.domain.disbursement;

import com.qlda.sontay.common.DisbursementStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(collection = "cl_disbursement_requests")
public class Disbursement {

    @Id
    ObjectId id;

    String code;

    ObjectId projectId;
    String projectName;

    String period;

    List<DisbursementItem> items;

    String note;

    List<String> milestones;

    Double advanceDeduction;

    Double completionPct;

    DisbursementStatus status;
    Instant submittedAt;
    Instant createdAt;
}
