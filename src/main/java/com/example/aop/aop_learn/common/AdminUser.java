package com.example.aop.aop_learn.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminUser {
    private long id;

    private String userId;

    private String note;

    @AllArgsConstructor
    public enum Status {
        Low("低级用户"),
        Normal("普通用户"),
        Admin("管理员"),
        SuperAdmin("超级管理员");
        String description;
    }

    private Status status = Status.Normal;
}
