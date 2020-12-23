package com.nameof.skeleton.integration.github.mapper;

import com.nameof.skeleton.integration.github.domain.Repository;
import com.nameof.skeleton.integration.github.response.GetRepositoryResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RepositoryMapper {
    @Autowired
    private ModelMapper modelMapper;

    public Repository responseToDomain(GetRepositoryResponse response) {
        return modelMapper.map(response, Repository.class);
    }
}
