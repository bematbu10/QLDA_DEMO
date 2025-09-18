package com.qlda.sontay.mapper.role;

import com.qlda.sontay.domain.Role;
import com.qlda.sontay.mapper.base.EntityMapper;
import com.qlda.sontay.dto.auth.response.RoleResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleResponseDTOMapper extends EntityMapper<RoleResponse, Role> {}
