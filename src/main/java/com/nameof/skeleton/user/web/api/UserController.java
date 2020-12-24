package com.nameof.skeleton.user.web.api;

import com.nameof.skeleton.user.mapper.UserMapper;
import com.nameof.skeleton.user.model.dto.UserDto;
import com.nameof.skeleton.user.service.UserService;
import com.nameof.skeleton.user.web.request.UserPageRequest;
import com.nameof.skeleton.user.web.request.UserSignupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;

    /**
     * @param request
     * @return
     */
    @PostMapping("/signup")
    public UserDto signup(@RequestBody @Valid UserSignupRequest request) {
        return userService.signup(userMapper.toUserSignupBo(request));
    }

    @GetMapping("/page")
    public Page<UserDto> queryPage(@RequestBody @Valid UserPageRequest request) {
        return userService.queryPage(userMapper.toUserPageBo(request));
    }
}
