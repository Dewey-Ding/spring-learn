package com.example.utils.kafkaDelay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;

/**
 * @author dewey
 */
public class KafkaDelayHealthIndicator implements HealthIndicator {

    @Autowired
    private KafkaDelayConsumerManager kafkaDelayConsumerManager;

    @Override
    public Health health() {
        Status status = kafkaDelayConsumerManager.health();
        if(Status.UP == status ){
            return Health.up().build();
        }else if(Status.OUT_OF_SERVICE == status){
            return Health.outOfService().build();
        }else {
            return Health.down().build();
        }

    }
}
