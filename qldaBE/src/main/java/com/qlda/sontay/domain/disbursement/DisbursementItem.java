package com.qlda.sontay.domain.disbursement;


import lombok.*;
import lombok.experimental.FieldDefaults;
import org.bson.types.ObjectId;

@Getter
@Setter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DisbursementItem {

    ObjectId id;

    String description;

    Double amount;

    Double taxRate;
}
