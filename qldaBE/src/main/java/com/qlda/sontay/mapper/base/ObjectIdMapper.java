package com.qlda.sontay.mapper.base;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

@Component
public class ObjectIdMapper {

    public String asString(ObjectId objectId) {
        return objectId != null ? objectId.toHexString() : null;
    }

    public ObjectId asObjectId(String id) {
        return (id != null && !id.isEmpty()) ? new ObjectId(id) : null;
    }
}
