package io.logik.graph_ql_poc.models.github;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Repositories {
    private int totalCount;
    private RepositoryResponse[] nodes;
    private Object pageInfo;
}
