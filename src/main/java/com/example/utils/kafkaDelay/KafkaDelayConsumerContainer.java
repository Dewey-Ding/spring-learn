package com.example.utils.kafkaDelay;

import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.lang.reflect.Method;

/**
 * @author dewey
 */
public class KafkaDelayConsumerContainer {

    /**
     * 消费逻辑方法所属bean
     */
    private Object bean;

    /**
     * 业务消费方法
     */
    private Method method;

    /**
     * 注解详情
     */
    private KafkaDelayListener kafkaDelayListener;

    /**
     * 监听方式，手动commit与自动commit
     */
    private KafkaDelayListenerType listenerType;

    /**
     * 消费者
     */
    private KafkaConsumer<String,String> consumer;

    /**
     * 提交位移逻辑
     */
    private ConsumerCommitter consumerCommitter;

    private final String paramTypeRecord = "org.apache.kafka.clients.consumer.ConsumerRecord";
    private final String paramTypeCommitter = "com.netease.com.tiku.kafka.delay.KafkaDelayCommitter";

    public KafkaDelayConsumerContainer(Object bean, Method method, KafkaDelayListener kafkaDelayListener) {
        this.bean = bean;
        this.method = method;
        this.kafkaDelayListener = kafkaDelayListener;

        Class<?>[] parameterTypes = method.getParameterTypes();
        if(parameterTypes.length <= 0 || parameterTypes.length >= 3){
            throw new RuntimeException(String.format("KafkaDelayConsumerContainer:Method[%s] parameter number error", method.getName()));
        }
        if(!paramTypeRecord.equals(parameterTypes[0].getName())){
            throw new RuntimeException(String.format("KafkaDelayConsumerContainer:Method[%s] parameter type error,need %s but find %s",
                    method.getName(),paramTypeRecord,parameterTypes[0].getName()));
        }
        if(parameterTypes.length > 1 && !paramTypeCommitter.equals(parameterTypes[1].getName())){
            throw new RuntimeException(String.format("KafkaDelayConsumerContainer:Method[%s] parameter type error,need %s but find %s",
                    method.getName(),paramTypeCommitter,parameterTypes[1].getName()));
        }

        if(parameterTypes.length >= 2){
            this.consumerCommitter = new ConsumerCommitter();
            listenerType = KafkaDelayListenerType.ACKNOWLEDGING;
        }else{
            listenerType = KafkaDelayListenerType.SIMPLE;
        }

    }



    /**
     * 封装提交逻辑
     */
    private final class ConsumerCommitter implements KafkaDelayCommitter {

        @Override
        public void commitSync() {
            processCommitSync();
        }

        @Override
        public void commitAsync() {
            processCommitAsync();
        }
    }

    private void processCommitSync(){
        consumer.commitSync();
    }

    private void processCommitAsync(){
        consumer.commitAsync();
    }

    public KafkaDelayListener getKafkaDelayListener() {
        return kafkaDelayListener;
    }

    public void setKafkaDelayListener(KafkaDelayListener kafkaDelayListener) {
        this.kafkaDelayListener = kafkaDelayListener;
    }

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public KafkaDelayListenerType getListenerType() {
        return listenerType;
    }

    public void setListenerType(KafkaDelayListenerType listenerType) {
        this.listenerType = listenerType;
    }

    public KafkaConsumer<String, String> getConsumer() {
        return consumer;
    }

    public void setConsumer(KafkaConsumer<String, String> consumer) {
        this.consumer = consumer;
    }

    public ConsumerCommitter getConsumerCommitter() {
        return consumerCommitter;
    }

    public void setConsumerCommitter(ConsumerCommitter consumerCommitter) {
        this.consumerCommitter = consumerCommitter;
    }
}
