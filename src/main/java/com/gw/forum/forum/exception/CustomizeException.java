package com.gw.forum.forum.exception;

public class CustomizeException extends RuntimeException {
    private String message;

    public CustomizeException(ICustomizaErrorCode errorCode) {
        this.message=errorCode.getMessage();
    }

    public CustomizeException(String message) {
        this.message=message;
    }
    @Override
    public String getMessage() {
        return message;
    }
}