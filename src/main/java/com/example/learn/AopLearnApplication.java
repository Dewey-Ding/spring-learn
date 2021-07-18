package com.example.learn;

import com.example.utils.kafkaDelay.EnableKafkaDelay;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@EnableKafkaDelay
public class AopLearnApplication {

    public static void main(String[] args) {
        SpringApplication.run(AopLearnApplication.class, args);
    }

}
