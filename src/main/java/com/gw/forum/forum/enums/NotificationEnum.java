package com.gw.forum.forum.enums;

public enum NotificationEnum {
    REPLY_QUESTION(1,"回复了问题"),
    REPLY_COMMENT(2,"回复了评论")
    ;
    private Integer status;
    private String name;

    NotificationEnum(Integer status, String name) {
        this.status = status;
        this.name = name;
    }

    public static String nameOfType(Integer type) {
        for (NotificationEnum notificationEnum:NotificationEnum.values())
            if (notificationEnum.getStatus()==type){
                return notificationEnum.getName();
            }
        return null;
    }

    public Integer getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

}
