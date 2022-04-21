package com.cedrus.samercsaleh.pingpong.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data

public class ServeBallResponse {
    @JsonProperty
    private ApiResponse apiResponse;

    public ServeBallResponse (boolean successInd){
        if (successInd){
            this.apiResponse = new ApiResponse();
            this.apiResponse.setSuccessInd(true);
            this.apiResponse.setCode("0");
            this.apiResponse.setMessage("Success");
        } else {
            this.apiResponse = new ApiResponse();
            this.apiResponse.setSuccessInd(false);
            this.apiResponse.setCode("-1");
            this.apiResponse.setMessage("Failure");
        }
    }
    public ServeBallResponse (boolean successInd, String message, String code){
        this.apiResponse = new ApiResponse();
        this.apiResponse.setSuccessInd(successInd);
        this.apiResponse.setMessage(message);
        this.apiResponse.setCode(code);
    }
}
