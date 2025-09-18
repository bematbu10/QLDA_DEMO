package com.qlda.sontay.dto.auth.request;

import lombok.Getter;
import org.bson.types.ObjectId;

import java.io.Serializable;

@Getter
public class UserPasswordRequest implements Serializable {
    private ObjectId id;
    private String password;
    private String confirmPassword;
}
