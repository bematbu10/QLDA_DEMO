package com.qlda.sontay.dto.auth.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qlda.sontay.common.Gender;
import com.qlda.sontay.common.UserType;
import com.qlda.sontay.validator.EnumValue;
import com.qlda.sontay.validator.GenderSubset;
import com.qlda.sontay.validator.PhoneNumber;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

import static com.qlda.sontay.common.Gender.*;


@Getter
public class UserRequest implements Serializable {

    @NotBlank(message = "fullName must be not blank") // Khong cho phep gia tri blank
    private String fullName;

    @GenderSubset(anyOf = {MALE, FEMALE, OTHER})// cách 2 validation
    private Gender gender;

    @NotNull(message = "dateOfBirth must be not null")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "MM/dd/yyyy")
    private Date birthday;

    @NotNull(message = "username must be not null")
    private String username;

    @NotNull(message = "password must be not null")
    private String password;

    @Email(message = "email invalid format") // Chi chap nhan nhung gia tri dung dinh dang email
    //@Pattern(regexp = "^\\d{10}$", message = "phone invalid format")
    private String email;

    @PhoneNumber(message = "phone invalid format")
    private String phone;

    @NotNull(message = "type must be not null")
    @EnumValue(name = "type", enumClass = UserType.class)// cách 3 validation
    private String type;

    @NotNull(message = "roleId must be not null")
    private String roleId;
}
