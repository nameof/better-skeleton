package com.nameof.skeleton.integration.github;

import com.nameof.skeleton.integration.github.domain.Repository;
import com.nameof.skeleton.integration.github.exec.RepositoryExec;
import com.nameof.skeleton.integration.github.mapper.RepositoryMapper;
import com.nameof.skeleton.integration.github.request.GetRepositoryRequest;
import com.nameof.skeleton.integration.github.response.GetRepositoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * just a stub
 */
@Component
public class GitHubClient {
    @Autowired
    private RepositoryMapper repositoryMapper;
    @Autowired
    private RepositoryExec repositoryExec;

    public Repository getRepository(String repoName) {
        GetRepositoryResponse response = repositoryExec.getRepository(repoName);
        return repositoryMapper.responseToDomain(response);
    }

}