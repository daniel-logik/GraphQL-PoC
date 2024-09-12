package io.logik.graph_ql_poc.models.starwars;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@NoArgsConstructor
public class StarWarsFilmList {
    List<StarWarsFilm> films;
}
