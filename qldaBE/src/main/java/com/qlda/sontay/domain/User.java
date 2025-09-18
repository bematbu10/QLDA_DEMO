package com.qlda.sontay.domain;

import com.qlda.sontay.common.Gender;
import com.qlda.sontay.common.UserStatus;
import com.qlda.sontay.common.UserType;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document(collection = "cl_user")
public class User{
    @Id
    @EqualsAndHashCode.Include
    private ObjectId id;

    @Field("full_name")
    private String fullName;

    @Field("username")
    @EqualsAndHashCode.Include
    private String username;

    @Field("password")
    private String password;

    @Field("gender")
    private Gender gender;

    @Field("date_of_birth")
    private Date birthday;

    @Field("email")
    private String email;

    @Field("phone")
    private String phone;

    @Field("is_deleted")
    private Boolean isDeleted;

    @Field("taskStatus")
    private UserStatus status;

    @DBRef
    private Role role;

    @DBRef
    private Token token;
}
