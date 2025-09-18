package com.qlda.sontay.service.auth.impl;

import com.qlda.sontay.common.UserStatus;
import com.qlda.sontay.domain.Role;
import com.qlda.sontay.domain.User;
import com.qlda.sontay.exception.ResourceNotFoundException;
import com.qlda.sontay.mapper.user.UserUpdateRequestDTOMapper;
import com.qlda.sontay.mapper.user.UserResponseDTOMapper;
import com.qlda.sontay.repository.auth.RoleRepository;
import com.qlda.sontay.repository.auth.UserRepository;
import com.qlda.sontay.service.auth.UserService;
import com.qlda.sontay.dto.auth.request.UserPasswordRequest;
import com.qlda.sontay.dto.auth.request.UserRequest;
import com.qlda.sontay.dto.auth.request.UserUpdateRequest;
import com.qlda.sontay.dto.auth.response.UserPageResponse;
import com.qlda.sontay.dto.auth.response.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j(topic = "USER-SERVICE")
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserResponseDTOMapper userResponseDTOMapper;

    private final UserUpdateRequestDTOMapper userUpdateRequestDTOMapper;


    @Override
    public UserPageResponse getAllUsersWithSortBy(String sortBy, int pageNo, int pageSize) {

        // xu ly truong hop FE muon bat dau voi page = 1
        if (pageNo > 0) {
            pageNo -= 1;
        }

        List<Sort.Order> sorts = new ArrayList<>();
        // nếu có giá trị
        if (StringUtils.hasLength(sortBy)) {
            // firstName: asc|desc
            Pattern pattern = Pattern.compile("(\\w+?)(:)(.*)");
            Matcher matcher = pattern.matcher(sortBy);
            if (matcher.find()) {
                if (matcher.group(3).equalsIgnoreCase("asc")) {
                    sorts.add(new Sort.Order(Sort.Direction.ASC, matcher.group(1)));
                } else {
                    sorts.add(new Sort.Order(Sort.Direction.DESC, matcher.group(1)));
                }
            }

        }

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sorts));
        Page<User> users = userRepository.findAll(pageable);

        return getUserPageResponse(pageNo, pageSize, users);
    }

    @Override
    public UserPageResponse getAllUsersWithSortByMultipleColumns(int pageNo, int pageSize, List<String> sorts) {
        // xu ly truong hop FE muon bat dau voi page = 1
        if (pageNo > 0) {
            pageNo -= 1;
        }
        List<Sort.Order> orders = new ArrayList<>();

        for (String sortBy : sorts) {

            Pattern pattern = Pattern.compile("(\\w+?)(:)(.*)");

            Matcher matcher = pattern.matcher(sortBy);
            if (matcher.find()) {
                if (matcher.group(3).equalsIgnoreCase("asc")) {
                    orders.add(new Sort.Order(Sort.Direction.ASC, matcher.group(1)));
                } else {
                    orders.add(new Sort.Order(Sort.Direction.DESC, matcher.group(1)));
                }
            }
        }


        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(orders));

        Page<User> users = userRepository.findAll(pageable);


        return getUserPageResponse(pageNo, pageSize, users);
    }

    /**
     * Convert UserEntities to user
     *
     * @param page
     * @param pageSize
     * @param userEntities
     * @return
     */
    private UserPageResponse getUserPageResponse(int page, int pageSize, Page<User> userEntities) {
        log.info("Convert User Entity Page");
        List<UserResponse> userList = userEntities.stream().map(entity -> UserResponse.builder()
            .id(entity.getId())
            .gender(entity.getGender())
            .birthday(entity.getBirthday())
            .username(entity.getUsername())
            .phone(entity.getPhone())
            .email(entity.getEmail())
            .build()
        ).toList();

        UserPageResponse response = new UserPageResponse();
        response.setPageNumber(page);
        response.setPageSize(pageSize);
        response.setTotalElements(userEntities.getTotalElements());
        response.setTotalPages(userEntities.getTotalPages());
        response.setUsers(userList);
        return response;
    }

    @Override
    public UserResponse findById(ObjectId id) {

        return userResponseDTOMapper.toDto(getUserEntity(id));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ObjectId saveUser(UserRequest req) {
        Role role = roleRepository.findById(req.getRoleId())
            .orElseThrow(() -> new RuntimeException("Role not found"));

        User user = User.builder()
            .gender(req.getGender())
            .birthday(req.getBirthday())
            .fullName(req.getFullName())
            .email(req.getEmail())
            .phone(req.getPhone())
            .username(req.getUsername())
            .password(passwordEncoder.encode(req.getPassword()))
            .status(UserStatus.ACTIVE)
            .isDeleted(false)
            .role(role)
            .build();

        userRepository.save(user);

        log.info("User has added successfully, userId={}", user.getId());

        return user.getId();
    }

    @Override
    @Transactional()
    public UserResponse update(ObjectId userId, UserUpdateRequest req) {
        log.info("Updating user: {}", req);
        // Get user by id
        User user = getUserEntity(userId);
        userUpdateRequestDTOMapper.partialUpdate(user, req);

        return userResponseDTOMapper.toDto(userRepository.save(user));
    }

    @Override
    public ObjectId changePassword(UserPasswordRequest req) {
        log.info("Changing password for user: {}", req);
        // Get user by id
        User user = getUserEntity(req.getId());
        if (req.getPassword().equals(req.getConfirmPassword())) {
            user.setPassword(passwordEncoder.encode(req.getPassword()));
        }

        userRepository.save(user);
        log.info("Changed password for user: {}", req);

        return user.getId();
    }

    @Override
    public ObjectId delete(ObjectId id) {
        log.info("Deleting user: {}", id);

        // Get user by id
        User user = getUserEntity(id);
        user.setStatus(UserStatus.INACTIVE);
        userRepository.save(user);
        log.info("Deleted user: {}", user);
        return id;
    }

    private User getUserEntity(ObjectId id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

}
