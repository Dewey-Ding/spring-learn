package com.example.aop.aop_learn.xxljob;


import org.springframework.stereotype.Service;

@Service
public class DemoJobService {
    public void demoTest(String s1){
        System.out.println("==============" + s1);
    }
}