package com.nameof.skeleton.user.service;

import com.nameof.skeleton.core.service.AbstractService;
import com.nameof.skeleton.user.domain.Role;
import com.nameof.skeleton.user.domain.User;
import com.nameof.skeleton.user.mapper.UserMapper;
import com.nameof.skeleton.user.model.dto.UserDto;
import com.nameof.skeleton.user.model.enums.UserRoles;
import com.nameof.skeleton.user.model.request.ChangePasswordRequest;
import com.nameof.skeleton.user.model.request.UpdateProfileRequest;
import com.nameof.skeleton.user.model.request.UserSignupRequest;
import com.nameof.skeleton.user.mq.UserMessageSender;
import com.nameof.skeleton.user.repository.RoleRepository;
import com.nameof.skeleton.user.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

import static com.nameof.skeleton.core.enums.EntityType.USER;
import static com.nameof.skeleton.core.enums.ExceptionType.*;

@Service
public class UserService extends AbstractService {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository repository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserMessageSender messageSender;

    @Transactional
    public UserDto signup(UserSignupRequest request) {
        Role userRole;
        User user = repository.findByEmail(request.getEmail());
        if (user == null) {
            userRole = roleRepository.findByRole(UserRoles.PASSENGER);
            user = userMapper.toDomain(request);
            user.setPassword(bCryptPasswordEncoder.encode(request.getPassword()))
                .setRoles(new HashSet<>(Collections.singletonList(userRole)));

            messageSender.sendUserSignup(user);
            return userMapper.toDto(repository.save(user));
        }
        throw exception(USER, DUPLICATE_ENTITY, request.getEmail());
    }

    @Transactional
    public UserDto findUserByEmail(String email) {
        Optional<User> user = Optional.ofNullable(repository.findByEmail(email));
        User userModel = user.orElseThrow(() -> exception(USER, ENTITY_NOT_FOUND, email));
        return userMapper.toDto(userModel);
    }

    public UserDto updateProfile(UpdateProfileRequest request) {
        Optional<User> user = Optional.ofNullable(repository.findByEmail(request.getEmail()));
        User userModel = user.orElseThrow(() -> exception(USER, ENTITY_NOT_FOUND, request.getEmail()));
        userModel.setFirstName(request.getFirstName())
                .setLastName(request.getLastName())
                .setMobileNumber(request.getMobileNumber());
        return userMapper.toDto(repository.save(userModel));
    }

    public UserDto changePassword(ChangePasswordRequest request) {
        Optional<User> user = Optional.ofNullable(repository.findByEmail(request.getEmail()));
        User userModel = user.orElseThrow(() -> exception(USER, ENTITY_NOT_FOUND, request.getEmail()));
        if (!userModel.getPassword().equals(bCryptPasswordEncoder.encode(request.getOldPassword()))) {
            throw businessException("password error");
        }
        userModel.setPassword(bCryptPasswordEncoder.encode(request.getNewPassword()));
        return userMapper.toDto(repository.save(userModel));
    }

    public Page<UserDto> queryPage(int page, int size, String firstName) {
        PageRequest request = PageRequest.of(page, size);
        Page<User> pageData = null;
        if (StringUtils.isNotEmpty(firstName)) {
            pageData = repository.findByFirstName(firstName, request);
        } else {
            pageData = repository.findAll(request);
        }
        return pageData.map(userMapper::toDto);
    }

    public Optional<UserDto> getById(Long id) {
        return repository.findById(id).map(userMapper::toDto);
    }
}