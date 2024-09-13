package io.logik.graph_ql_poc.service;

import io.logik.graph_ql_poc.models.github.Repositories;

import io.logik.graph_ql_poc.models.github.RepositoryResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.graphql.client.GraphQlClient;
import org.springframework.graphql.client.HttpGraphQlClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class GitHubClientServiceTest {

    @Mock
    private HttpGraphQlClient mockGraphQlClient;

    @InjectMocks
    private GitHubClientService gitHubClientService;

    @Test
    void getRepository() {
        mockGraphClient();

        Repositories response = gitHubClientService.getRepository(3);
        assertThat(response.getTotalCount()).isEqualTo(2);
        assertThat(response.getNodes()[0].getNameWithOwner()).isEqualTo("abc-repoName");

    }

    @Test
    void getRepositoryOwnerID() {
        GraphQlClient.RequestSpec requestSpec = Mockito.mock(GraphQlClient.RequestSpec.class);
        GraphQlClient.RetrieveSyncSpec retrieveSpec = Mockito.mock(GraphQlClient.RetrieveSyncSpec.class);

        when(mockGraphQlClient.documentName("getRepositoryOwner")).thenReturn(requestSpec);
        when(requestSpec.variable(any(), any())).thenReturn(requestSpec);
        when(requestSpec.retrieveSync("repositoryOwner.id")).thenReturn(retrieveSpec);
        when(retrieveSpec.toEntity(String.class)).thenReturn("fakeIdIsHere");


        String ownerId = gitHubClientService.getRepositoryOwnerID("daniel-logik");
        assertThat(ownerId).isNotNull();
        assertThat(ownerId).isNotEmpty();
    }

    private void mockGraphClient() {
        Repositories expectedResponse = new Repositories();
        RepositoryResponse[] repos = new RepositoryResponse[2];
        repos[0] = new RepositoryResponse();
        repos[1] = new RepositoryResponse();
        repos[0].setNameWithOwner("abc-repoName");
        repos[1].setNameWithOwner("def-noName");
        expectedResponse.setNodes(repos);
        expectedResponse.setTotalCount(repos.length);

        // mock RequestSpec
        GraphQlClient.RequestSpec requestSpec = Mockito.mock(GraphQlClient.RequestSpec.class);
        // mock RetriveSpec
        GraphQlClient.RetrieveSyncSpec retrieveSpec = Mockito.mock(GraphQlClient.RetrieveSyncSpec.class);

        when(mockGraphQlClient.documentName("getRepositories")).thenReturn(requestSpec);
        when(requestSpec.variables((any()))).thenReturn(requestSpec);
        when(requestSpec.retrieveSync("viewer.repositories")).thenReturn(retrieveSpec);
        when(retrieveSpec.toEntity(Repositories.class)).thenReturn(expectedResponse);

    }

}