package com.cedrus.samercsaleh.pingpong.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data //generates all the boilerplate that is normally associated with simple java objects
@JsonInclude(JsonInclude.Include.NON_NULL) //create fields only when the value is not null

public class ApiResponse {
    @JsonProperty
    private boolean successInd; //tells Jackson ObjectMapper to map the JSON property name to the annotated Java field's name for serdes
    @JsonProperty
    private String message;
    @JsonProperty
    private String code;
}

/* note on model packages:
Model often holds either data structures for a database, interface objects (this case) or other items of data structure.
This class defines the structures, what it contains, some logic if appropriate for building/updating the object, and also information for how to (de)serialize it, using Jackson.
Jackson is an API that serializes and deserializes XML and JSON so that you don't have to worry about that.
 */
