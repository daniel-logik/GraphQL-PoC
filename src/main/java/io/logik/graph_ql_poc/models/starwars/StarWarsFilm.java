package io.logik.graph_ql_poc.models.starwars;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Data
@NoArgsConstructor
public class StarWarsFilm {
    private String title;
    private String director;
    private String releaseDate;
    private Map<String, StarWarsSpecies[]> speciesConnection;
}
