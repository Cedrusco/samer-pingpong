package com.cedrus.samercsaleh.pingpong.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data

public class ServeBallRequest {
    @JsonProperty
    private String id;
    @JsonProperty
    private String ballName;
    @JsonProperty
    private String color;
}
