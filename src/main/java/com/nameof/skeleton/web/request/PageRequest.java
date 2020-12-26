package com.nameof.skeleton.web.request;

import lombok.Data;

@Data
public class PageRequest  {
    private int page = 1;
    private int size = 10;
}
