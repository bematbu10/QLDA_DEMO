package com.qlda.sontay.domain.disbursement;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(collection = "cl_plan_items")
public class PlanItem {
    @Id
    ObjectId id;

    String period;
    Double plannedAmount;
}
