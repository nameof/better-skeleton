package com.nameof.skeleton.util;

import com.nameof.skeleton.web.response.ErrorResponse;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.stream.Collectors;

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

    public static List<String> errorToMsg(List<ObjectError> allError){
        return allError.stream()
                .map(objectError -> {
                    if (objectError instanceof FieldError) {
                        FieldError fe = (FieldError) objectError;
                        return fe.getField() + ":" + fe.getDefaultMessage();
                    }
                    return objectError.getDefaultMessage();
                }).collect(Collectors.toList());
    }
}