package com.qlda.sontay.service.auth;


import com.qlda.sontay.dto.auth.request.RoleRequest;
import com.qlda.sontay.dto.auth.response.RoleResponse;

import java.util.List;

public interface RoleService {
    RoleResponse create(RoleRequest request);

    List<RoleResponse> getAll();

    String delete(String roleId);
}
