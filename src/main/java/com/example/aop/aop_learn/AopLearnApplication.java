package com.example.aop.aop_learn;

import com.example.aop.utils.EnableKafkaDelay;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableKafkaDelay
//@EnableScheduling
public class AopLearnApplication {

    public static void main(String[] args) {
        SpringApplication.run(AopLearnApplication.class, args);
    }

}
