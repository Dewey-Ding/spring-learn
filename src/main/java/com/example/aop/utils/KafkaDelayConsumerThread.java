package com.example.aop.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Collections;

/**
 * @author dewey
 */
@Slf4j
public class KafkaDelayConsumerThread extends Thread {

    private KafkaConsumer<String, String> consumer;

    private Method method;

    private Object bean;

    private String topic;

    private KafkaDelayCommitter committer;

    private long pauseTime;

    private long delayTime;

    private boolean autoCommit;

    /**
     * 用户Damon判断消费者是否正常
     * todo 后面优化kafkaconsumer的状态判断
     */
    private String consumerStatus;

    private static String CONSUMER_STATUS_RUNNING = "normal";

    private static String CONSUMER_STATUS_PAUSE = "pause";

    public KafkaDelayConsumerThread(KafkaDelayConsumerContainer consumerContainer) {
        KafkaDelayListener kafkaDelayListener = consumerContainer.getKafkaDelayListener();

        this.consumer = consumerContainer.getConsumer();
        this.method = consumerContainer.getMethod();
        this.bean = consumerContainer.getBean();
        this.topic = kafkaDelayListener.topic();

        if (consumerContainer.getListenerType().equals(KafkaDelayListenerType.ACKNOWLEDGING)) {
            this.autoCommit = false;
            this.committer = consumerContainer.getConsumerCommitter();
        } else {
            autoCommit = true;
        }
        //转换为毫秒
        this.pauseTime = kafkaDelayListener.pauseTimeSec() * 1000;
        this.delayTime = kafkaDelayListener.delayTimeSec() * 1000;
    }


    @Override
    public void run() {
        //订阅
        consumer.subscribe(Collections.singleton(topic));
        boolean paused = false;
        while (true) {
            if (paused) {
                try {
                    Thread.sleep(pauseTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (log.isDebugEnabled()) {
                    log.debug("KafkaDelayConsumerManager:topic{},消费重启", topic);
                }
                this.consumerStatus = "pause";
                consumer.resume(consumer.paused());
                paused = false;
            } else {
                this.consumerStatus = "run";
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(200));
                for (ConsumerRecord<String, String> consumerRecord : records) {
                    TopicPartition topicPartition = new TopicPartition(consumerRecord.topic(), consumerRecord.partition());
                    if (consumerRecord.timestamp() > System.currentTimeMillis() - delayTime) {
                        if (log.isDebugEnabled()) {
                            log.debug("KafkaDelayConsumerManager:topic{},消费暂停", topic);
                        }
                        paused = true;

                        consumer.pause(Collections.singleton(topicPartition));
                        consumer.seek(topicPartition, consumerRecord.offset());
                        break;
                    } else {
                        try {
                            if (autoCommit) {
                                method.invoke(bean, consumerRecord);
                            } else {
                                method.invoke(bean, consumerRecord, committer);
                            }
                        } catch (Exception e) {
                            log.error("KafkaDelayConsumerManager:consume fail,topic:{},message{}", topic, consumerRecord.value());
                        }
                    }
                }
            }
        }
    }


    public String getConsumerStatus() {
        return consumerStatus;
    }

}
