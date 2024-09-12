package io.logik.graph_ql_poc.models.starwars;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@Component
public class StarWarsHomeworld {
    private String name;
}
