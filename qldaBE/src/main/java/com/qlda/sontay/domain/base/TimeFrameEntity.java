package com.qlda.sontay.domain.base;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PROTECTED)
public abstract class TimeFrameEntity extends BaseEntity {
    @Field("start_date")
    Date startDate;

    @Field("end_date")
    Date endDate;
}
