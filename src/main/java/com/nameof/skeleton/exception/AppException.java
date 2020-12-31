package com.nameof.skeleton.exception;

import com.nameof.skeleton.core.enums.EntityType;
import com.nameof.skeleton.core.enums.ExceptionType;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.StringJoiner;

@Component
public class AppException {

    /**
     * Returns new RuntimeException based on EntityType, ExceptionType and args
     *
     * @param entityType
     * @param exceptionType
     * @param args
     * @return
     */
    public static RuntimeException throwException(EntityType entityType, ExceptionType exceptionType, Object... args) {
        String messageDesc = getMessageDesc(entityType, exceptionType);
        return throwException(exceptionType, messageDesc, args);
    }

    /**
     * Returns new RuntimeException based on template and args
     *
     * @param messageDesc
     * @param args
     * @return
     */
    private static RuntimeException throwException(ExceptionType exceptionType, String messageDesc, Object... args) {
        if (ExceptionType.ENTITY_NOT_FOUND.equals(exceptionType)) {
            return new EntityNotFoundException(format(messageDesc, args));
        } else if (ExceptionType.DUPLICATE_ENTITY.equals(exceptionType)) {
            return new DuplicateEntityException(format(messageDesc, args));
        }
        return new RuntimeException(format(messageDesc, args));
    }

    private static String getMessageDesc(EntityType entityType, ExceptionType exceptionType) {
        return entityType.name().concat(" ").concat(exceptionType.getValue()).toLowerCase();
    }

    private static String format(String desc, Object ... args) {
        if (args.length == 0) {
            return desc;
        }
        String result = desc + ": ";
        StringJoiner sj = new StringJoiner(", ");
        for (Object arg : args) {
            sj.add(Objects.toString(arg));
        }
        return result + sj.toString();
    }

    public static class EntityNotFoundException extends RuntimeException {
        public EntityNotFoundException(String message) {
            super(message);
        }
    }

    public static class DuplicateEntityException extends RuntimeException {
        public DuplicateEntityException(String message) {
            super(message);
        }
    }

}
