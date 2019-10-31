package com.gw.forum.forum.enums;

public enum  NotificationStatusEnum {
    UNREAD(1),READ(2);
    private Integer status;

    NotificationStatusEnum(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }
}
