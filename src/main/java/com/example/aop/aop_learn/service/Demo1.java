package com.example.aop.aop_learn.service;

import com.example.aop.aop_learn.service.intef.demo;
import org.springframework.stereotype.Service;

@Service
public class Demo1 implements demo {
    @Override
    public void isTrue() {
        System.out.println("yes");
    }
}
