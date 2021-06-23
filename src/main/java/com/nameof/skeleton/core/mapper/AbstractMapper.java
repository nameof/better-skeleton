package com.nameof.skeleton.core.mapper;

public abstract class AbstractMapper<Domain, Dto> {

    public abstract Dto toDto(Domain domain);
}
