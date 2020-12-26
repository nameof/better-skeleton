package com.nameof.skeleton.core.mapper;

public abstract class AbstractMapper<Domain, Dto> {
    public abstract Domain toDomain(Dto dto);

    public abstract Dto toDto(Domain domain);
}
