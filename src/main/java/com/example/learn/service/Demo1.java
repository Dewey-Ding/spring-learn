package com.example.learn.service;

import com.example.learn.service.intef.demo;
import org.springframework.stereotype.Service;

@Service
public class Demo1 implements demo {
    @Override
    public void isTrue() {
        System.out.println("yes");
    }
}
