package com.nscorp.obis.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nscorp.obis.response.data.ResponseStatusCode;
import lombok.Data;

import java.util.List;

@Data
public class ResponseObject {

    private List<String> messages;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;
    private String status;
    private int statusCode;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Error> errors;

    //Success
    public ResponseObject(List<String> messages, Object data, ResponseStatusCode responseStatusCode) {
        this(messages, data, responseStatusCode.getStatusCode(), responseStatusCode.toString());
    }

    //Success
    public ResponseObject(List<String> messages, Object data, int statusCode, String status) {
        this.messages = messages;
        this.data = data;
        this.status = status;
        this.statusCode = statusCode;
    }

    // Information
    public ResponseObject(List<String> messages, ResponseStatusCode responseStatusCode) {
        this(messages, responseStatusCode.getStatusCode(), responseStatusCode.toString());
    }

    // Information
    public ResponseObject(List<String> messages, int statusCode, String status) {
        this.messages = messages;
        this.status = status;
        this.statusCode = statusCode;
    }


    // Failure
    public ResponseObject(List<String> messages, List<Error> errors, ResponseStatusCode responseStatusCode) {
        this(messages, errors, responseStatusCode.getStatusCode(), responseStatusCode.toString());
    }

    // Failure
    public ResponseObject(List<String> messages, List<Error> errors, int statusCode, String status) {
        this.errors = errors;
        this.messages = messages;
        this.status = status;
        this.statusCode = statusCode;
    }
}