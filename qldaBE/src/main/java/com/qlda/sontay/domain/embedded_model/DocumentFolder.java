package com.qlda.sontay.domain.embedded_model;
import lombok.*;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.bson.types.ObjectId;

import java.util.List;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DocumentFolder {
    @Builder.Default
    String id = new ObjectId().toHexString();
    String name;
    List<DocumentFolder> subfolders;
    List<BaseDocument> files;
}
