package com.nameof.skeleton.web.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.nameof.skeleton.core.enums.Status;
import com.nameof.skeleton.utils.DateUtil;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponse {

    private Status status;
    private Date timestamp;
    private Object payload;
    private List<Error> errors;
    private Object metadata;

    public ErrorResponse() {
        this.timestamp = DateUtil.today();
    }

    public static ErrorResponse badRequest() {
        ErrorResponse response = new ErrorResponse();
        response.setStatus(Status.BAD_REQUEST);
        return response;
    }

    public static  ErrorResponse exception() {
        ErrorResponse response = new ErrorResponse();
        response.setStatus(Status.EXCEPTION);
        return response;
    }

    public static  ErrorResponse notFound() {
        ErrorResponse response = new ErrorResponse();
        response.setStatus(Status.NOT_FOUND);
        return response;
    }

    public static  ErrorResponse duplicateEntity() {
        ErrorResponse response = new ErrorResponse();
        response.setStatus(Status.DUPLICATE_ENTITY);
        return response;
    }

    public void addError(String errorMsg, Exception ex) {
        if (errors == null) {
            errors = new ArrayList<>();
        }
        errors.add(new Error()
                .setMessage(errorMsg)
                .setDetails(ex.getMessage()));
    }

    public void addError(Error error) {
        if (errors == null) {
            errors = new ArrayList<>();
        }
        errors.add(error);
    }

    @Data
    @Accessors(chain = true)
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Error {
        private String message;
        private String details;
    }
}