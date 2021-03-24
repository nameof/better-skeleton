package com.nameof.skeleton.integration.github;


import com.alibaba.testable.core.annotation.MockMethod;
import com.alibaba.testable.core.model.MockScope;
import com.nameof.skeleton.integration.github.domain.Repository;
import com.nameof.skeleton.integration.github.exec.RepositoryExec;
import com.nameof.skeleton.integration.github.mapper.RepositoryMapper;
import com.nameof.skeleton.integration.github.response.GetRepositoryResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.alibaba.testable.core.matcher.InvokeVerifier.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GitHubClientTest {
    @Autowired GitHubClient gitHubClient;

    public static class Mock{
        @MockMethod(targetClass = RepositoryExec.class, scope = MockScope.ASSOCIATED)
        public GetRepositoryResponse getRepository(String repoName) {
            return new GetRepositoryResponse();
        }

        @MockMethod(targetClass = RepositoryMapper.class, scope = MockScope.ASSOCIATED)
        public Repository responseToDomain(GetRepositoryResponse response) {
            return new Repository("mock",  1, 1);
        }
    }

    @Test
    public void testGetRepository() {
        Repository repo = gitHubClient.getRepository("a");
        Assert.assertTrue(repo.getName().equals("mock"));
        Assert.assertTrue(repo.getFork() == 1);
        Assert.assertTrue(repo.getStar() == 1);
        verify("getRepository").with("a");
        verify("responseToDomain");
    }

}
