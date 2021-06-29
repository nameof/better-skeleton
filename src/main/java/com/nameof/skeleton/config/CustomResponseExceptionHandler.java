package com.nameof.skeleton.config;

import com.nameof.skeleton.exception.AppException;
import com.nameof.skeleton.utils.ValidatorUtil;
import com.nameof.skeleton.web.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@ControllerAdvice
@RestController
public class CustomResponseExceptionHandler {

    @ExceptionHandler(AppException.EntityNotFoundException.class)
    public final ResponseEntity handleNotFountExceptions(AppException.EntityNotFoundException ex, WebRequest request) {
        ErrorResponse response = new ErrorResponse();
        response.addError("not found", ex);
        return new ResponseEntity(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AppException.DuplicateEntityException.class)
    public final ResponseEntity handleDuplicateException(AppException.DuplicateEntityException ex, WebRequest request) {
        ErrorResponse response = new ErrorResponse();
        response.addError("duplicate error", ex);
        return new ResponseEntity(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity handleArgException(MethodArgumentNotValidException e) {
        ErrorResponse response = new ErrorResponse();
        List<ObjectError> allError = e.getBindingResult().getAllErrors();
        ValidatorUtil.errorToMsg(allError).stream()
                .map(msg -> new ErrorResponse.Error().setMessage("argument error").setDetails(msg))
                .forEach(response::addError);
        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AppException.BusinessException.class)
    public final ResponseEntity handleBusinessException(AppException.BusinessException ex) {
        ErrorResponse response = new ErrorResponse();
        response.addError("business error", ex);
        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity handleException(Exception ex) {
        ErrorResponse response = new ErrorResponse();
        response.addError("unkown error", ex);
        return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
