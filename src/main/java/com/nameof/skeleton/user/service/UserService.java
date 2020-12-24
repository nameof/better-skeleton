package com.nameof.skeleton.user.service;

import com.nameof.skeleton.core.service.AbstractService;
import com.nameof.skeleton.user.domain.Role;
import com.nameof.skeleton.user.domain.User;
import com.nameof.skeleton.user.model.enums.UserRoles;
import com.nameof.skeleton.user.mapper.UserMapper;
import com.nameof.skeleton.user.model.bo.UserPageBo;
import com.nameof.skeleton.user.model.bo.UserSignupBo;
import com.nameof.skeleton.user.model.dto.UserDto;
import com.nameof.skeleton.user.repository.RoleRepository;
import com.nameof.skeleton.user.repository.UserRepository;
import org.modelmapper.ModelMapper;
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
    private ModelMapper modelMapper;

    public UserDto signup(UserSignupBo bo) {
        Role userRole;
        User user = repository.findByEmail(bo.getEmail());
        if (user == null) {
            userRole = roleRepository.findByRole(UserRoles.PASSENGER);
            user = new User()
                    .setEmail(bo.getEmail())
                    .setPassword(bCryptPasswordEncoder.encode(bo.getPassword()))
                    .setRoles(new HashSet<>(Arrays.asList(userRole)))
                    .setFirstName(bo.getFirstName())
                    .setLastName(bo.getLastName())
                    .setMobileNumber(bo.getMobileNumber());
            return userMapper.toUserDto(repository.save(user));
        }
        throw exception(USER, DUPLICATE_ENTITY, bo.getEmail());
    }

    @Transactional
    public UserDto findUserByEmail(String email) {
        Optional<User> user = Optional.ofNullable(repository.findByEmail(email));
        if (user.isPresent()) {
            return modelMapper.map(user.get(), UserDto.class);
        }
        throw exception(USER, ENTITY_NOT_FOUND, email);
    }

    public UserDto updateProfile(UserDto userDto) {
        Optional<User> user = Optional.ofNullable(repository.findByEmail(userDto.getEmail()));
        if (user.isPresent()) {
            User userModel = user.get();
            userModel.setFirstName(userDto.getFirstName())
                    .setLastName(userDto.getLastName())
                    .setMobileNumber(userDto.getMobileNumber());
            return userMapper.toUserDto(repository.save(userModel));
        }
        throw exception(USER, ENTITY_NOT_FOUND, userDto.getEmail());
    }

    public UserDto changePassword(UserDto userDto, String newPassword) {
        Optional<User> user = Optional.ofNullable(repository.findByEmail(userDto.getEmail()));
        if (user.isPresent()) {
            User userModel = user.get();
            userModel.setPassword(bCryptPasswordEncoder.encode(newPassword));
            return userMapper.toUserDto(repository.save(userModel));
        }
        throw exception(USER, ENTITY_NOT_FOUND, userDto.getEmail());
    }

    public Page<UserDto> queryPage(UserPageBo bo) {
        PageRequest request = PageRequest.of(bo.getPage(), bo.getSize());
        Page<User> page = repository.findAll(request);
        List<UserDto> list = page.getContent().stream().map(userMapper::toUserDto).collect(Collectors.toList());
        return new PageImpl<>(list, request, page.getTotalElements());
    }

}