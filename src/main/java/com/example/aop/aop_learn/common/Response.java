package com.example.aop.aop_learn.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class Response implements Serializable {
    private Integer code;
    private Object result;
    private String msg;
}
