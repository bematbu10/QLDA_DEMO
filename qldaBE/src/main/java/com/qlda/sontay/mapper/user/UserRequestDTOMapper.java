package com.qlda.sontay.mapper.user;

import com.qlda.sontay.domain.User;
import com.qlda.sontay.mapper.base.EntityMapper;
import com.qlda.sontay.dto.auth.request.UserRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserRequestDTOMapper extends EntityMapper<UserRequest, User> {
}
