package com.social.quarkus.dto;

public class FieldError {
    private String fieldError;
    private String message;

    public FieldError(String fieldError, String message) {
        this.fieldError = fieldError;
        this.message = message;
    }

    public String getFieldError() {
        return fieldError;
    }

    public void setFieldError(String fieldError) {
        this.fieldError = fieldError;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
