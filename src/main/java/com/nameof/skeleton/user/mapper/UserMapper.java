package com.nameof.skeleton.user.mapper;

import com.nameof.skeleton.user.domain.User;
import com.nameof.skeleton.user.model.bo.UserPageBo;
import com.nameof.skeleton.user.model.bo.UserSignupBo;
import com.nameof.skeleton.user.model.dto.RoleDto;
import com.nameof.skeleton.user.model.dto.UserDto;
import com.nameof.skeleton.user.web.request.UserPageRequest;
import com.nameof.skeleton.user.web.request.UserSignupRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.stream.Collectors;


@Component
public class UserMapper {
    @Autowired
    private ModelMapper modelMapper;

    public UserPageBo toUserPageBo(UserPageRequest request) {
        return modelMapper.map(request, UserPageBo.class);
    }

    public UserSignupBo toUserSignupBo(UserSignupRequest request) {
        return modelMapper.map(request, UserSignupBo.class);
    }

    public UserDto toUserDto(User user) {
        return new UserDto()
                .setEmail(user.getEmail())
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName())
                .setMobileNumber(user.getMobileNumber())
                .setRoles(new HashSet<RoleDto>(user
                        .getRoles()
                        .stream()
                        .map(role -> new ModelMapper().map(role, RoleDto.class))
                        .collect(Collectors.toSet())));
    }
}
