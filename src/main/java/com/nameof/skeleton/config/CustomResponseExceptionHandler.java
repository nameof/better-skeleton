package com.nameof.skeleton.config;

import com.nameof.skeleton.exception.AppException;
import com.nameof.skeleton.utils.ValidatorUtil;
import com.nameof.skeleton.web.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

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

    // 缺少参数
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public final ResponseEntity handleMissingArgException(MissingServletRequestParameterException e) {
        ErrorResponse response = new ErrorResponse();
        response.addError(new ErrorResponse.Error("argument error", "缺少参数：" + e.getParameterName()));
        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    }

    // 标量参数校验失败
    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity handleArgException(ConstraintViolationException e) {
        ErrorResponse response = new ErrorResponse();
        ValidatorUtil.constraintViolationToMsg(e.getConstraintViolations()).stream()
                .map(msg -> new ErrorResponse.Error().setMessage("argument error").setDetails(msg))
                .forEach(response::addError);
        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    }

    // GET请求，对象参数校验失败
    @ExceptionHandler(BindException.class)
    public final ResponseEntity handleGetArgException(BindException e) {
        List<ObjectError> allError = e.getBindingResult().getAllErrors();
        return new ResponseEntity(toError(allError), HttpStatus.BAD_REQUEST);
    }

    // POST请求，对象参数校验失败
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity handlePostArgException(MethodArgumentNotValidException e) {
        List<ObjectError> allError = e.getBindingResult().getAllErrors();
        return new ResponseEntity(toError(allError), HttpStatus.BAD_REQUEST);
    }

    private ErrorResponse toError(List<ObjectError> allError) {
        ErrorResponse response = new ErrorResponse();
        ValidatorUtil.objectErrorToMsg(allError).stream()
                .map(msg -> new ErrorResponse.Error().setMessage("argument error").setDetails(msg))
                .forEach(response::addError);
        return response;
    }

    @ExceptionHandler(AppException.BusinessException.class)
    public final ResponseEntity handleBusinessException(AppException.BusinessException ex) {
        ErrorResponse response = new ErrorResponse();
        response.addError("business error", ex);
        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Throwable.class)
    public final ResponseEntity handleException(Throwable ex) {
        ErrorResponse response = new ErrorResponse();
        response.addError("unkown error", ex);
        return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
