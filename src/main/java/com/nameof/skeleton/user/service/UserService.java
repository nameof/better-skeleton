package com.nameof.skeleton.user.service;

import com.nameof.skeleton.core.service.AbstractService;
import com.nameof.skeleton.user.domain.Role;
import com.nameof.skeleton.user.domain.User;
import com.nameof.skeleton.user.mapper.UserMapper;
import com.nameof.skeleton.user.model.dto.UserDto;
import com.nameof.skeleton.user.model.enums.UserRoles;
import com.nameof.skeleton.user.mq.UserMessageSender;
import com.nameof.skeleton.user.repository.RoleRepository;
import com.nameof.skeleton.user.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.nameof.skeleton.core.enums.EntityType.USER;
import static com.nameof.skeleton.core.enums.ExceptionType.DUPLICATE_ENTITY;
import static com.nameof.skeleton.core.enums.ExceptionType.ENTITY_NOT_FOUND;

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
    public UserDto signup(UserDto dto) {
        Role userRole;
        User user = repository.findByEmail(dto.getEmail());
        if (user == null) {
            userRole = roleRepository.findByRole(UserRoles.PASSENGER);
            user = userMapper.toDomain(dto);
            user.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()))
                .setRoles(new HashSet<>(Arrays.asList(userRole)));

            messageSender.sendUserSignup(user);
            return userMapper.toDto(repository.save(user));
        }
        throw exception(USER, DUPLICATE_ENTITY, dto.getEmail());
    }

    @Transactional
    public UserDto findUserByEmail(String email) {
        Optional<User> user = Optional.ofNullable(repository.findByEmail(email));
        User userModel = user.orElseThrow(() -> exception(USER, ENTITY_NOT_FOUND, email));
        return userMapper.toDto(userModel);
    }

    public UserDto updateProfile(UserDto userDto) {
        Optional<User> user = Optional.ofNullable(repository.findByEmail(userDto.getEmail()));
        User userModel = user.orElseThrow(() -> exception(USER, ENTITY_NOT_FOUND, userDto.getEmail()));
        userModel.setFirstName(userDto.getFirstName())
                .setLastName(userDto.getLastName())
                .setMobileNumber(userDto.getMobileNumber());
        return userMapper.toDto(repository.save(userModel));
    }

    public UserDto changePassword(UserDto userDto, String newPassword) {
        Optional<User> user = Optional.ofNullable(repository.findByEmail(userDto.getEmail()));
        User userModel = user.orElseThrow(() -> exception(USER, ENTITY_NOT_FOUND, userDto.getEmail()));
        userModel.setPassword(bCryptPasswordEncoder.encode(newPassword));
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
        List<UserDto> list = pageData.getContent().stream().map(userMapper::toDto).collect(Collectors.toList());
        return new PageImpl<>(list, request, pageData.getTotalElements());
    }

    public Optional<UserDto> getById(Long id) {
        return repository.findById(id).map(userMapper::toDto);
    }
}