package com.nameof.skeleton.integration.github.exec;

import com.nameof.skeleton.integration.github.request.GetRepositoryRequest;
import com.nameof.skeleton.integration.github.response.GetRepositoryResponse;
import org.springframework.stereotype.Component;

@Component
public class RepositoryExec {
    public GetRepositoryResponse getRepository(String repoName) {
        GetRepositoryRequest request = makeRequest(repoName);
        return request(request);
    }

    private GetRepositoryResponse request(GetRepositoryRequest request) {
        return new GetRepositoryResponse(request.getName(), 0, 0);
    }

    private GetRepositoryRequest makeRequest(String repoName) {
        return new GetRepositoryRequest(repoName);
    }
}
