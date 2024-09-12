package io.logik.graph_ql_poc.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.logik.graph_ql_poc.models.starwars.StarWarsFilmList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.graphql.client.HttpSyncGraphQlClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;


// This file was put together just to test out different clients, and learn to deal
// with and map the responses to objects
@Service
public class StarWarsClientService {

    // Leaving this here, but it shouldn't be used with GraphQL - I used to help figure out why the
    // other clients were erroring during development
    @Autowired
    private RestTemplate baseRestTemplate;

    // This client is based on a RestClient, so it is blocking
    //
    @Autowired
    private HttpSyncGraphQlClient httpSyncGraphQlClient;

    // This client is based on WebClient, so it is non-blocking
    @Autowired
    private HttpGraphQlClient httpGraphQlClient;

    // Simple query to be made
    private final String exampleQuery =
    """
        query Query {
          allFilms {
            films {
              title
              director
              releaseDate
              speciesConnection {
                species {
                  name
                  classification
                  homeworld {
                    name
                  }
                }
              }
            }
          }
        }
    """;

    private final String endpoint1 = "https://swapi-graphql.netlify.app/.netlify/functions/index";

    // This is why we don't use restTemplate for this, its significantly messier than the other options
    public StarWarsFilmList getFilmsWithRestClient() throws JsonProcessingException {
        // headers set up
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // request body set up (This is where pretty much everything important to graphQl lives
        // If we wanted to send variables with the query we would also have a 'variables' entry in this map
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("query", exampleQuery);
        // Build and execute request
        HttpEntity<Object> entity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = baseRestTemplate.postForEntity(endpoint1, entity, String.class);

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Map> data = mapper.readValue(response.getBody(), HashMap.class);
        // parse into the actual list of films, and map
        Object films = data.get("data").get("allFilms");
        StarWarsFilmList filmList = mapper.convertValue(films, StarWarsFilmList.class);

        System.out.println(filmList);
        return filmList;
    }

    public StarWarsFilmList getFilmsWithSyncGraphClient() {
        StarWarsFilmList films = httpSyncGraphQlClient
                .document(exampleQuery)                 // This is the line that loads the query
                .retrieveSync("allFilms")          // This reads into the response object at the given path
                .toEntity(StarWarsFilmList.class);      // Map to output
        System.out.println(films);
        return films;
    }

    public StarWarsFilmList getFilmsWithHttpGraphClient() {
        StarWarsFilmList films = httpGraphQlClient
                .document(exampleQuery)
                .retrieveSync("allFilms")
                .toEntity(StarWarsFilmList.class);
        System.out.println(films);
        return films;
    }
}
