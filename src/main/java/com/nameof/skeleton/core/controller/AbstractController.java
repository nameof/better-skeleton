package com.nameof.skeleton.core.controller;

import com.nameof.skeleton.core.enums.EntityType;
import com.nameof.skeleton.core.enums.ExceptionType;
import com.nameof.skeleton.exception.AppException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractController {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected RuntimeException exception(EntityType entityType, ExceptionType exceptionType, Object... args) {
        return AppException.throwException(entityType, exceptionType, args);
    }
}
