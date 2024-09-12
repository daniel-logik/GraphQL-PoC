package io.logik.graph_ql_poc.models.starwars;


import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
@JsonTypeName("species")
public class StarWarsSpecies {
    private String name;
    private String classification;
    private StarWarsHomeworld homeworld;
}
