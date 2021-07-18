package com.example.aop.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author dewey
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface KafkaDelayListener {
    String topic();

    /**
     * 延迟消费的时间,单位秒
     * @return
     */
    int delayTimeSec() default 120;

    /**
     * 消费者pause时间,单位秒
     * @return
     */
    int pauseTimeSec() default 2;
}
