package com.nameof.skeleton.user.model.bo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class UserSignupBo {
    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private String mobileNumber;
}
