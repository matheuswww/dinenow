package com.github.matheuswwwp.dinenow.conf.CustomValidator;

import java.io.Serializable;
import java.util.Map;

public class RestResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private String message;
    private Integer statusCode;
    private String error;
    private Map<String,String> causes;

    public RestResponse(String message, Integer statusCode, String error, Map<String, String> causes) {
        this.message = message;
        this.statusCode = statusCode;
        this.error = error;
        this.causes = causes;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Map<String, String> getCauses() {
        return causes;
    }

    public void setCauses(Map<String, String> causes) {
        this.causes = causes;
    }
}
