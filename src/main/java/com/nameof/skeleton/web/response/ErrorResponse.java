package com.nameof.skeleton.web.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
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

    private Date timestamp;
    private Object payload;
    private List<Error> errors;
    private Object metadata;

    public ErrorResponse() {
        this.timestamp = DateUtil.today();
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