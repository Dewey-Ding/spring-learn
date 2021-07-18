package com.example.aop.utils;

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
