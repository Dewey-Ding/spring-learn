package com.example.utils.kafkaDelay;

/**
 * @author dewey
 */
public interface KafkaDelayCommitter {
    /**
     * 封装kafka consumer的commitSync
     */
    void commitSync();

    /**
     * 封装kafka consumer的commitAsync
     */
    void commitAsync();
}
