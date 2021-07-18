package com.example.utils.kafkaDelay;

/**
 * @author dewey
 */
public enum KafkaDelayListenerType {

    /**
     * 只接受消息模式,autocommit必须置为true
     */
    SIMPLE,

    /**
     * 业务控制commit，autocommit必须为false
     */
    ACKNOWLEDGING

}
