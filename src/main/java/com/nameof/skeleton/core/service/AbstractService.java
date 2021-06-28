package com.nameof.skeleton.core.service;

import com.nameof.skeleton.core.enums.EntityType;
import com.nameof.skeleton.core.enums.ExceptionType;
import com.nameof.skeleton.exception.AppException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractService {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected RuntimeException exception(EntityType entityType, ExceptionType exceptionType, String... args) {
        return AppException.throwException(entityType, exceptionType, args);
    }

    protected AppException.BusinessException businessException(String message) {
        return new AppException.BusinessException(message);
    }
}
