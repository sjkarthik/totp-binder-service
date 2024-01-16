package io.mosip.totpbinderservice.DTO;

import java.util.List;

public class KeyBindResponseDTO {
    private String responseTime;
    private String response;
    private List<Error> errors;

    public String getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(String responseTime) {
        this.responseTime = responseTime;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<Error> getErrors() {
        return errors;
    }

    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }
}
