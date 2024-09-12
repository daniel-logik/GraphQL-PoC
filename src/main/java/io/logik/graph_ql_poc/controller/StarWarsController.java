package io.logik.graph_ql_poc.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.logik.graph_ql_poc.models.starwars.StarWarsFilmList;
import io.logik.graph_ql_poc.service.StarWarsClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class StarWarsController {

    @Autowired
    private StarWarsClientService starWarsClientService;

    // This request is manually built and uses a RestTemplate, not ideal but works
    @QueryMapping
    @PostMapping(path = "/getFilmsRestClient")
    public ResponseEntity<StarWarsFilmList> queryMethod() throws JsonProcessingException {
        System.out.println("Hit Query Endpoint");
        StarWarsFilmList films = starWarsClientService.getFilmsWithRestClient();
        return ResponseEntity.ok(films);
    }

    // This uses the graphQL client based on restClient -- blocking
    @QueryMapping
    @PostMapping(path = "/getFilmsHttpSyncGraphQL")
    public ResponseEntity<StarWarsFilmList> queryMethodHttpSync() {
        StarWarsFilmList films = starWarsClientService.getFilmsWithSyncGraphClient();
        return ResponseEntity.ok(films);
    }

    // This uses the graphQL client based on WebClient -- nonBlocking (except I use retrieveSync which I assume blocks)
    // If I just used retrieve, I'd get back a mono (single element subscriber) that could be handled reactively
    // (think observable in angular, the api is similar)
    @QueryMapping
    @PostMapping(path = "/getFilmsHttpGraphQL")
    public ResponseEntity<StarWarsFilmList> queryMethodHttp() {
        StarWarsFilmList films = starWarsClientService.getFilmsWithHttpGraphClient();
        return ResponseEntity.ok(films);
    }
}
