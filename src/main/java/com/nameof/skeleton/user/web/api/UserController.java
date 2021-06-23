package com.nameof.skeleton.user.web.api;

import com.nameof.skeleton.core.controller.AbstractController;
import com.nameof.skeleton.core.enums.EntityType;
import com.nameof.skeleton.core.enums.ExceptionType;
import com.nameof.skeleton.user.mapper.UserMapper;
import com.nameof.skeleton.user.model.dto.UserDto;
import com.nameof.skeleton.user.service.UserService;
import com.nameof.skeleton.user.model.request.UserSignupRequest;
import com.nameof.skeleton.web.request.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/user")
public class UserController extends AbstractController {
    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public UserDto signup(@RequestBody @Valid UserSignupRequest request) {
        return userService.signup(request);
    }

    @GetMapping("/{id}")
    public UserDto getById(@PathVariable("id") Long id) {
        return userService.getById(id).orElseThrow(() ->  exception(EntityType.USER, ExceptionType.ENTITY_NOT_FOUND, id));
    }

    @GetMapping("/page")
    public Page<UserDto> queryPage(PageRequest request, String firstName) {
        return userService.queryPage(request.getPage(), request.getSize(), firstName);
    }
}
