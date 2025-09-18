package com.qlda.sontay.dto.disbusement;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DisbursementItemDTO {
    String id;
    String description;
    Double amount;
    Double taxRate;
}
