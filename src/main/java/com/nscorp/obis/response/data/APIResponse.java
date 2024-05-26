package com.nscorp.obis.response.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nscorp.obis.exception.Error;
import lombok.Data;

import java.util.List;

@Data
public class APIResponse<T> {

    private List<String> messages;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;
    private String status;
    private int statusCode;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Error> errors;

    //Success
    public APIResponse(List<String> messages, T data, ResponseStatusCode responseStatusCode) {
        this(messages, data, responseStatusCode.getStatusCode(), responseStatusCode.toString());
    }

    //Success
    public APIResponse(List<String> messages, T data, int statusCode, String status) {
        this.messages = messages;
        this.data = data;
        this.status = status;
        this.statusCode = statusCode;
    }

    // Information
    public APIResponse(List<String> messages, ResponseStatusCode responseStatusCode) {
        this(messages, responseStatusCode.getStatusCode(), responseStatusCode.toString());
    }

    // Information
    public APIResponse(List<String> messages, int statusCode, String status) {
        this.messages = messages;
        this.status = status;
        this.statusCode = statusCode;
    }


    // Failure
    public APIResponse(List<String> messages, List<Error> errors, ResponseStatusCode responseStatusCode) {
        this(messages, errors, responseStatusCode.getStatusCode(), responseStatusCode.toString());
    }

    // Failure
    public APIResponse(List<String> messages, List<Error> errors, int statusCode, String status) {
        this.errors = errors;
        this.messages = messages;
        this.status = status;
        this.statusCode = statusCode;
    }
}