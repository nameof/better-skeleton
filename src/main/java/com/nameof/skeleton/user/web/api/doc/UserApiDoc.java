package com.nameof.skeleton.user.web.api.doc;

import com.nameof.skeleton.user.model.dto.UserDto;
import com.nameof.skeleton.user.model.request.UserSignupRequest;
import com.nameof.skeleton.web.request.PageRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Api(tags = "User Api")
public interface UserApiDoc {
    @ApiOperation("用户注册")
    UserDto signup(@RequestBody @Valid @ApiParam(name = "注册数据", value = "JSON", required = true) UserSignupRequest request);

    UserDto getById(@PathVariable("id") Long id);

    Page<UserDto> queryPage(PageRequest request, String firstName);
}
