package com.gw.forum.forum.exception;

public enum  CustomizaErrorCode implements ICustomizaErrorCode{
    REQUEST_OK(2000,"请求成功"),
    QUESTION_NOT_FOUND(2001,"你找到的问题不在了，要不要换个试试?"),
    TARGET_PARAM_NOT_FOUND(2002,"未选中如何问题或评论进行回复。"),
    NO_LOGIN(2003,"未登录不能进行评论，请先登录！"),
    SYS_ERROR(2004,"服务太热啦，要不然等一下再来试试?"),
    TYPE_PARAM_WRONG(2005,"评论类型错误或不存在。"),
    COMMENT_NOT_FOUND(2006,"你找到的回复不在了，要不要换个试试?"),
    COMMENT_IS_EMPTY(2007,"输入内容不能为空")
    ;

    private Integer code;
    private String message;

    CustomizaErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }
    public String getMessage() {
        return message;
    }
    public Integer getCode() { return code; }
}
