package io.logik.graph_ql_poc.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.logik.graph_ql_poc.models.starwars.StarWarsFilmList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class StarWarsClientServiceTest {

    @Autowired
    private StarWarsClientService starWarsClientService;

    @Test
    void getFilmsWithRestClient() throws JsonProcessingException {
        StarWarsFilmList films = starWarsClientService.getFilmsWithRestClient();
        assertForGetFilms(films);
    }

    @Test
    void getFilmsWithSyncGraphClient() {
        StarWarsFilmList films = starWarsClientService.getFilmsWithSyncGraphClient();
        assertForGetFilms(films);
    }

    @Test
    void getFilmsWithHttpGraphClient() {
        StarWarsFilmList films = starWarsClientService.getFilmsWithHttpGraphClient();
        assertForGetFilms(films);
    }

    private void assertForGetFilms(StarWarsFilmList films) {
        assertThat(films.getFilms().size()).isEqualTo(6);
        assertThat(films.getFilms().get(0).getTitle()).isEqualTo("A New Hope");
        assertThat(films.getFilms().get(0).getSpeciesConnection().get("species").length).isEqualTo(5);
    }
}