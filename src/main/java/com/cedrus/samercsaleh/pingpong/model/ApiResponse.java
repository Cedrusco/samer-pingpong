package com.cedrus.samercsaleh.pingpong.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)

public class ApiResponse {
    @JsonProperty
    private boolean successInd;
    @JsonProperty
    private String message;
    @JsonProperty
    private String code;
}


