package com.qlda.sontay.dto.auth.response;


import com.qlda.sontay.common.Gender;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.bson.types.ObjectId;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse implements Serializable {
    ObjectId id;
    Gender gender;
    Date birthday;
    String username;
    String email;
    String phone;
    String fullName;
    RoleResponse role;
}
