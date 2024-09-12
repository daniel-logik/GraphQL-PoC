package io.logik.graph_ql_poc.models.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateRepositoryDto {
    private String repoName;
    private String ownerName;
}
