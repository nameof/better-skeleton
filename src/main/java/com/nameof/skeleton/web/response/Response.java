package com.nameof.skeleton.web.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.nameof.skeleton.core.enums.Status;
import com.nameof.skeleton.util.DateUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Response {

    private Status status;
    private ResponseError payload;
    private Object errors;
    private Object metadata;

    public static  Response badRequest() {
        Response response = new Response();
        response.setStatus(Status.BAD_REQUEST);
        return response;
    }

    public static  Response ok() {
        Response response = new Response();
        response.setStatus(Status.OK);
        return response;
    }

    public static  Response unauthorized() {
        Response response = new Response();
        response.setStatus(Status.UNAUTHORIZED);
        return response;
    }

    public static  Response validationException() {
        Response response = new Response();
        response.setStatus(Status.VALIDATION_EXCEPTION);
        return response;
    }

    public static  Response wrongCredentials() {
        Response response = new Response();
        response.setStatus(Status.WRONG_CREDENTIALS);
        return response;
    }

    public static  Response accessDenied() {
        Response response = new Response();
        response.setStatus(Status.ACCESS_DENIED);
        return response;
    }

    public static  Response exception() {
        Response response = new Response();
        response.setStatus(Status.EXCEPTION);
        return response;
    }

    public static  Response notFound() {
        Response response = new Response();
        response.setStatus(Status.NOT_FOUND);
        return response;
    }

    public static  Response duplicateEntity() {
        Response response = new Response();
        response.setStatus(Status.DUPLICATE_ENTITY);
        return response;
    }

    public void addErrorMsgToResponse(String errorMsg, Exception ex) {
        ResponseError error = new ResponseError()
                .setDetails(errorMsg)
                .setMessage(ex.getMessage())
                .setTimestamp(DateUtils.today());
        setErrors(error);
    }

}

