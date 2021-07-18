package com.example.learn;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootTest

@EnableAspectJAutoProxy(proxyTargetClass=true,exposeProxy = true)
class AopLearnApplicationTests {

    @Test
    void contextLoads() {
    }

}
