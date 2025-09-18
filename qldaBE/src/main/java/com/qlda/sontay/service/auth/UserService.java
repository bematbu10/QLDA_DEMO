package com.qlda.sontay.service.auth;

import com.qlda.sontay.dto.auth.request.UserPasswordRequest;
import com.qlda.sontay.dto.auth.request.UserRequest;
import com.qlda.sontay.dto.auth.request.UserUpdateRequest;
import com.qlda.sontay.dto.auth.response.UserPageResponse;
import com.qlda.sontay.dto.auth.response.UserResponse;
import org.bson.types.ObjectId;

import java.util.List;

public interface UserService {

    UserPageResponse getAllUsersWithSortBy(String sort, int page, int size);

    UserPageResponse getAllUsersWithSortByMultipleColumns(int pageNo, int pageSize, List<String> sorts);

    UserResponse findById(ObjectId id);

    ObjectId saveUser(UserRequest user);

    UserResponse update(ObjectId userId , UserUpdateRequest user);

    ObjectId changePassword(UserPasswordRequest oldPassword);

    ObjectId delete(ObjectId id);
}
