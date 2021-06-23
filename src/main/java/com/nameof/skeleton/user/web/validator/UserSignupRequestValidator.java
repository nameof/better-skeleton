package com.nameof.skeleton.user.web.validator;

import com.nameof.skeleton.user.model.request.UserSignupRequest;
import com.nameof.skeleton.utils.ValidatorUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserSignupRequestValidator implements ConstraintValidator<UserSignupRequestValid, UserSignupRequest> {
    @Override
    public boolean isValid(UserSignupRequest userSignupRequest, ConstraintValidatorContext context) {
        if (false) { //some custom validate logic
            ValidatorUtil.setMessage(context, "this is an error msg");
            return false;
        }
        return true;
    }
}