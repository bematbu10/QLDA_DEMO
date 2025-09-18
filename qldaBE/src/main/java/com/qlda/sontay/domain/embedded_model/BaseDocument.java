package com.qlda.sontay.domain.embedded_model;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;

import java.util.Date;


@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED)
public class BaseDocument  {
    @Builder.Default
    String id = new ObjectId().toHexString();
    String name;
    String url;
    @Builder.Default
    Date uploadedAt = new Date();
    String type; // pdf, docx, jpg, png, xlsx, other
}
