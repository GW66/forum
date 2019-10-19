package com.gw.forum.forum.exception;

public enum  CustomizaErrorCode implements ICustomizaErrorCode{
    QUESTION_NOT_FOUND("你找到的问题不在了，要不要换个试试?");

    private String message;
    CustomizaErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
