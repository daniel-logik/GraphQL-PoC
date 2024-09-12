package io.logik.graph_ql_poc.models.github;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RepositoryResponse {
    private String nameWithOwner;
}
