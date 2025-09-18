package com.qlda.sontay.service.auth.impl;


import com.qlda.sontay.mapper.role.RoleRequestDTOMapper;
import com.qlda.sontay.mapper.role.RoleResponseDTOMapper;
import com.qlda.sontay.repository.auth.RoleRepository;
import com.qlda.sontay.service.auth.RoleService;
import com.qlda.sontay.dto.auth.request.RoleRequest;
import com.qlda.sontay.dto.auth.response.RoleResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
public class RoleServiceImpl implements RoleService {
    RoleRepository roleRepository;

    RoleRequestDTOMapper roleRequestDTOMapper;

    RoleResponseDTOMapper roleResponseDTOMapper;

    @Transactional
    @Override
    public RoleResponse create(RoleRequest request){
        var role = roleRequestDTOMapper.toEntity(request);
        role.setId(new ObjectId().toHexString());
        role.setIsDeleted(false);
        role.setIsActivated(true);
        role = roleRepository.save(role);
        return roleResponseDTOMapper.toDto(role);
    }

    @Override
    public List<RoleResponse> getAll(){
        return roleRepository.findAll()
                .stream()
                .map(roleResponseDTOMapper::toDto)
                .toList();
    }
    @Override
    public String delete(String roleId){
        roleRepository.deleteById(roleId);
        return roleId;
    }


}
