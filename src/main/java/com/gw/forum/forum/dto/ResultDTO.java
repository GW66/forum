package com.gw.forum.forum.dto;

import com.gw.forum.forum.exception.CustomizaErrorCode;
import com.gw.forum.forum.exception.CustomizeException;

public class ResultDTO<T> {
    private Integer code;
    private String message;
    private  T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static ResultDTO errorOf(Integer code, String message){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }
    public static ResultDTO errorOf(CustomizeException e){
        return errorOf(e.getCode(),e.getMessage());
    }
    public static ResultDTO errorOf(CustomizaErrorCode e) {
        return errorOf(e.getCode(),e.getMessage());
    }
    public static <T>ResultDTO data(T t){
        ResultDTO resultDTO=new ResultDTO();
        resultDTO.setCode(CustomizaErrorCode.REQUEST_OK.getCode());
        resultDTO.setMessage(CustomizaErrorCode.REQUEST_OK.getMessage());
        resultDTO.setData(t);
        return resultDTO;
    }
}
