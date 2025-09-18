package com.qlda.sontay.mapper.role;

import com.qlda.sontay.domain.Role;
import com.qlda.sontay.mapper.base.EntityMapper;
import com.qlda.sontay.dto.auth.request.RoleRequest;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface RoleRequestDTOMapper extends EntityMapper<RoleRequest, Role> {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "isActivated", ignore = true)
    Role toEntity(RoleRequest dto);

}
