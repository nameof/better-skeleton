package com.nameof.skeleton.util;

import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;

import javax.validation.ConstraintValidatorContext;

public class ValidatorUtil {
    /**
     * 自定义的验证消息
     * @param context
     * @param message 验证提示消息
     */
    public static void setMessage(ConstraintValidatorContext context, String message){
        if(ConstraintValidatorContextImpl.class.isAssignableFrom(context.getClass())){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
        }
    }
}