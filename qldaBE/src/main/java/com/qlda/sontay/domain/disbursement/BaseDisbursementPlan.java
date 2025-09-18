package com.qlda.sontay.domain.disbursement;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(collection = "cl_disbursement_plans")
public class BaseDisbursementPlan {

    @Id
    ObjectId id;

    List<PlanItem> items;
}
