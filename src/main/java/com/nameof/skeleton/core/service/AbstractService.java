package com.nameof.skeleton.core.service;

import com.nameof.skeleton.exception.BRSException;
import com.nameof.skeleton.core.enums.EntityType;
import com.nameof.skeleton.core.enums.ExceptionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AbstractService {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected RuntimeException exception(EntityType entityType, ExceptionType exceptionType, String... args) {
        return BRSException.throwException(entityType, exceptionType, args);
    }
}
