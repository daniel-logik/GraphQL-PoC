package io.logik.graph_ql_poc.models.github;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.logik.graph_ql_poc.models.dto.BaseGraphQlRequestVariables;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateRepositoryRequestVariables extends BaseGraphQlRequestVariables {
    private String loginId;
    private String repoName;

//    public Map<String, Object> toVariables() {
//        ObjectMapper mapper = new ObjectMapper();
//        return mapper.convertValue(this, new TypeReference<Map<String, Object>>() {});
//    }
}
