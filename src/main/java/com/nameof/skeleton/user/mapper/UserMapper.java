package com.nameof.skeleton.user.mapper;

import com.nameof.skeleton.core.mapper.AbstractMapper;
import com.nameof.skeleton.user.domain.User;
import com.nameof.skeleton.user.model.dto.UserDto;
import com.nameof.skeleton.user.web.request.UserSignupRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;


@Component
public class UserMapper extends AbstractMapper<User, UserDto> {

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public UserDto toDto(User user) {
        return new UserDto()
                .setEmail(user.getEmail())
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName())
                .setMobileNumber(user.getMobileNumber())
                .setRoles(user
                        .getRoles()
                        .stream()
                        .map(roleMapper::toDto)
                        .collect(Collectors.toSet()));
    }

    @Override
    public User toDomain(UserDto dto) {
        User user = new User()
                .setEmail(dto.getEmail())
                .setFirstName(dto.getFirstName())
                .setLastName(dto.getLastName())
                .setMobileNumber(dto.getMobileNumber())
                .setRoles(dto.getRoles().stream().map(roleMapper::toDomain).collect(Collectors.toList()));
        return user;
    }

    public UserDto toDto(UserSignupRequest request) {
        return mapper.map(request, UserDto.class);
    }
}
