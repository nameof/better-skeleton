package com.nameof.skeleton.user.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nameof.skeleton.user.web.validator.UserSignupRequestValid;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.NotBlank;


@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@UserSignupRequestValid
public class UserSignupRequest {
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private String mobileNumber;
}
