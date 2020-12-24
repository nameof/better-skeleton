package com.nameof.skeleton.user.web.request;

import lombok.Data;

@Data
public class UserPageRequest {
    private int page;
    private int size;
}
