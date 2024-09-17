package io.logik.graph_ql_poc.service;

import io.logik.graph_ql_poc.models.github.CreateRepositoryRequestVariables;
import io.logik.graph_ql_poc.models.github.Repositories;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.client.HttpGraphQlClient;

import org.springframework.stereotype.Service;

import java.util.Map;

@Log4j2
@Service
public class GitHubClientService {
    @Autowired
    private HttpGraphQlClient gitGraphQlClient;

    private String githubAPIEndpoint = "https://api.github.com/graphql";

    @PostConstruct
    public void initializeClientWithCorrectEndpoint()
    {
        // This makes a mutated copy of the client so we can have a single HttpGraphQlClient bean that is used everywhere
        gitGraphQlClient = gitGraphQlClient.mutate()
                .url(githubAPIEndpoint)
                .build();
    }

    // Github token needed (PAT passed into controller as queryParam)
    public void setClientToken(String token) {
        gitGraphQlClient = gitGraphQlClient.mutate()
                .header("Authorization", "bearer %s".formatted(token))
                .build();
        log.info("Token Set for Github Requests");
    }

    public Repositories getRepository(long numberOfRepos) {
        Map<String, Object> variables = Map.of("number_of_repos", numberOfRepos);

        log.info("Sending getRepository Query");

        Repositories response = gitGraphQlClient
                .documentName("getRepositories")    // This references graphql files under resources/graphql-documents
                .variables(variables)               // Setting variables with a Map
                .retrieveSync("viewer.repositories")              // Will return the whole object
                .toEntity(Repositories.class);            // Map to Object to not have to set up Types to map to --

        log.debug(response);

        return response;

    }

    public String getRepositoryOwnerID(String login) {

        String id = gitGraphQlClient
                .documentName("getRepositoryOwner")
                .variable("login", login)              // Single variable passed without need for map creation
                .retrieveSync("repositoryOwner.id")     // Reads into nested path repositoryOwner -> id
                .toEntity(String.class);
        return id;
    }

    public Object createRepository(String repoOwner, String repoName) {

        log.info("Attempting Repository Creation");
        String id = getRepositoryOwnerID(repoOwner);
        // Map<String, Object> variables = Map.of("loginId", id, "repoName", repoName);
        CreateRepositoryRequestVariables variables = new CreateRepositoryRequestVariables(id, repoName);
        Object response = gitGraphQlClient
                .documentName("createRepository")
                .variables(variables.toVariables())
                .retrieveSync("")
                .toEntity(Object.class);

        return response;
    }

}
