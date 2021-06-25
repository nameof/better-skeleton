package com.nameof.skeleton.user.model.request;

import lombok.Data;

@Data
public class UpdateProfileRequest {
    private String email;
    private String firstName;
    private String lastName;
    private String mobileNumber;
}
