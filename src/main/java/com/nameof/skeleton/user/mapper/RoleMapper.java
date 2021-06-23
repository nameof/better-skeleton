package com.nameof.skeleton.user.mapper;

import com.nameof.skeleton.core.mapper.AbstractMapper;
import com.nameof.skeleton.user.domain.Role;
import com.nameof.skeleton.user.model.dto.RoleDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper extends AbstractMapper<Role, RoleDto> {
    @Autowired
    private ModelMapper mapper;

    public Role toDomain(RoleDto roleDto) {
        return mapper.map(roleDto, Role.class);
    }

    @Override
    public RoleDto toDto(Role role) {
        return mapper.map(role, RoleDto.class);
    }
}
