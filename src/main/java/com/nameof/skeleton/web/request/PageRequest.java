package com.nameof.skeleton.web.request;

import org.springframework.data.domain.Sort;

public class PageRequest extends org.springframework.data.domain.PageRequest  {

    public PageRequest(int page, int size) {
        this(page, size, Sort.unsorted());
    }

    protected PageRequest(int page, int size, Sort sort) {
        super(page, size, sort);
    }
}
