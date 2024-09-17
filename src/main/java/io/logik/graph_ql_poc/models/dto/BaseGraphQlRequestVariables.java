package io.logik.graph_ql_poc.models.dto;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.util.Map;

public abstract class BaseGraphQlRequestVariables {

    public Map<String, Object> toVariables() {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(this, new TypeReference<Map<String, Object>>() {});
    }
}
