package io.logik.graph_ql_poc.controller;

import io.logik.graph_ql_poc.models.dto.CreateRepositoryDto;
import io.logik.graph_ql_poc.service.GitHubClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GitHubController {

    @Autowired
    private GitHubClientService gitHubClientService;

    @QueryMapping
    @PostMapping("/getRepositories")
    public ResponseEntity<Object> getRepositories(@RequestParam("max") long max, @RequestParam(required = false, name="token") String token) {
        if (token != null && !token.isEmpty()) {
            gitHubClientService.setClientToken(token);
        }
        Object response = gitHubClientService.getRepository(max);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/createRepository")
    public ResponseEntity<Object> createRepository(@RequestBody CreateRepositoryDto createRepoDto,
                                                   @RequestParam(required = false, name="token") String token) {
        // This isn't ideal token handling but
        if (token != null && !token.isEmpty()) {
            gitHubClientService.setClientToken(token);
        }
        Object response = gitHubClientService.createRepository(createRepoDto.getOwnerName(), createRepoDto.getRepoName());
        return ResponseEntity.ok(response);

    }

}
