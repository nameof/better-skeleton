package com.nameof.skeleton.user.service;

import com.nameof.skeleton.user.request.UserSignupRequest;
import com.nameof.skeleton.exception.BRSException;
import com.nameof.skeleton.exception.EntityType;
import com.nameof.skeleton.exception.ExceptionType;
import com.nameof.skeleton.user.mapper.UserMapper;
import com.nameof.skeleton.user.dto.UserDto;
import com.nameof.skeleton.user.domain.Role;
import com.nameof.skeleton.user.domain.User;
import com.nameof.skeleton.user.domain.UserRoles;
import com.nameof.skeleton.user.repository.user.RoleRepository;
import com.nameof.skeleton.user.repository.user.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import static com.nameof.skeleton.exception.EntityType.USER;
import static com.nameof.skeleton.exception.ExceptionType.DUPLICATE_ENTITY;
import static com.nameof.skeleton.exception.ExceptionType.ENTITY_NOT_FOUND;

@Service
public class UserService {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public UserDto signup(UserSignupRequest request) {
        Role userRole;
        User user = userRepository.findByEmail(request.getEmail());
        if (user == null) {
            if (request.isAdmin()) {
                userRole = roleRepository.findByRole(UserRoles.ADMIN);
            } else {
                userRole = roleRepository.findByRole(UserRoles.PASSENGER);
            }
            user = new User()
                    .setEmail(request.getEmail())
                    .setPassword(bCryptPasswordEncoder.encode(request.getPassword()))
                    .setRoles(new HashSet<>(Arrays.asList(userRole)))
                    .setFirstName(request.getFirstName())
                    .setLastName(request.getLastName())
                    .setMobileNumber(request.getMobileNumber());
            return UserMapper.toUserDto(userRepository.save(user));
        }
        throw exception(USER, DUPLICATE_ENTITY, request.getEmail());
    }

    @Transactional
    public UserDto findUserByEmail(String email) {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(email));
        if (user.isPresent()) {
            return modelMapper.map(user.get(), UserDto.class);
        }
        throw exception(USER, ENTITY_NOT_FOUND, email);
    }

    public UserDto updateProfile(UserDto userDto) {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(userDto.getEmail()));
        if (user.isPresent()) {
            User userModel = user.get();
            userModel.setFirstName(userDto.getFirstName())
                    .setLastName(userDto.getLastName())
                    .setMobileNumber(userDto.getMobileNumber());
            return UserMapper.toUserDto(userRepository.save(userModel));
        }
        throw exception(USER, ENTITY_NOT_FOUND, userDto.getEmail());
    }

    public UserDto changePassword(UserDto userDto, String newPassword) {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(userDto.getEmail()));
        if (user.isPresent()) {
            User userModel = user.get();
            userModel.setPassword(bCryptPasswordEncoder.encode(newPassword));
            return UserMapper.toUserDto(userRepository.save(userModel));
        }
        throw exception(USER, ENTITY_NOT_FOUND, userDto.getEmail());
    }

    private RuntimeException exception(EntityType entityType, ExceptionType exceptionType, String... args) {
        return BRSException.throwException(entityType, exceptionType, args);
    }
}