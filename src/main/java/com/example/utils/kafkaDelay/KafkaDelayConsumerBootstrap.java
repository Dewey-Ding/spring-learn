package com.example.utils.kafkaDelay;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

/**
 * @author dewey
 */
@Configuration
@Slf4j
@EnableConfigurationProperties(KafkaDelayConfig.class)
public class KafkaDelayConsumerBootstrap {

    @Bean("kafkaDelayConsumerManager")
    public KafkaDelayConsumerManager kafkaDelayConsumerManager(){
        log.info("=====init kafkaDelayConsumerManager");
        return new KafkaDelayConsumerManager();
    }

    @Bean("kafkaDelayHealthIndicator")
    @DependsOn("kafkaDelayConsumerManager")
    public KafkaDelayHealthIndicator kafkaDelayHealthIndicator(){
        log.info("=====init KafkaDelayHealthIndicator");
        return new KafkaDelayHealthIndicator();
    }
}
