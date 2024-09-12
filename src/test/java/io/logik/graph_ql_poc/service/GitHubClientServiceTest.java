package io.logik.graph_ql_poc.service;

import io.logik.graph_ql_poc.models.github.Repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class GitHubClientServiceTest {

    @Autowired
    private GitHubClientService gitHubClientService;
    // Erase before adding - fill with your Git PAT to test
    private String gitToken = "";

    @BeforeEach
    void setup() {
        if (gitToken == null || gitToken.isEmpty()) {
            throw new RuntimeException("Token empty or not found, requests will not work.  " +
                    "Make sure to edit line 18 by pasting your github PAT. Must have repository read scope");
        }
        gitHubClientService.initializeClientWithCorrectEndpoint();
        gitHubClientService.setClientToken(gitToken);
    }

    @Test
    void getRepository() {
        Repositories response = gitHubClientService.getRepository(3);
        assertThat(response.getTotalCount()).isEqualTo(5);
        assertThat(response.getNodes()[0].getNameWithOwner()).isEqualTo("daniel-logik/TestEmptyRepository");

    }

    @Test
    void getRepositoryOwnerID() {
        String ownerId = gitHubClientService.getRepositoryOwnerID("daniel-logik");
        assertThat(ownerId).isNotNull();
        assertThat(ownerId).isNotEmpty();
    }

}