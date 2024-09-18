package io.logik.graph_ql_poc.models.request;


import io.logik.graph_ql_poc.models.request.BaseGraphQlRequestVariables;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
